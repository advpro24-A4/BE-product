package id.ac.ui.cs.advprog.youkosoproduct.service;
import id.ac.ui.cs.advprog.youkosoproduct.dto.NotificationDTO;
import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.OrderItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;
import id.ac.ui.cs.advprog.youkosoproduct.model.enumaration.NotificationType;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IOrderRepository;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {
    private IOrderRepository orderRepository;
    private IPaymentRepository paymentRepository;
    private PaymentServiceImpl paymentService;

    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(IOrderRepository.class);
        paymentRepository = mock(IPaymentRepository.class);
        kafkaService = mock(KafkaService.class);
        paymentService = new PaymentServiceImpl(orderRepository, paymentRepository, kafkaService);
    }

    @Test
    void payment_OrderExistsAndPaymentNotExists_CreatesPayment() {
        Long orderId = 1L;
        String paymentMethod = "CREDIT_CARD";
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(200);
        order.setOrderItems(List.of(orderItem));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.empty());
        when(paymentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = paymentService.payment(orderId, paymentMethod, userId);

        assertNotNull(result);
        assertNotNull(result.getPayment());
        assertEquals("PENDING", result.getPayment().getPaymentStatus());
        assertEquals(orderId, result.getPayment().getOrderId());
        assertEquals(userId, result.getPayment().getUserId());
    }

    @Test
    void payment_OrderNotFound_ThrowsNotFoundException() {
        long orderId = 1L;
        String paymentMethod = "CREDIT_CARD";
        String userId = "user123";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            paymentService.payment(orderId, paymentMethod, userId);
        });
    }

    @Test
    void payment_UserIdMismatch_ThrowsNotFoundException() {
        Long orderId = 1L;
        String paymentMethod = "CREDIT_CARD";
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId("differentUser");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(NotFoundException.class, () -> {
            paymentService.payment(orderId, paymentMethod, userId);
        });
    }

    @Test
    void payment_PendingPaymentExists_ThrowsBadRequestException() {
        Long orderId = 1L;
        String paymentMethod = "CREDIT_CARD";
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);

        Payment existingPayment = new Payment();
        existingPayment.setPaymentStatus("PENDING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.of(existingPayment));

        assertThrows(BadRequestException.class, () -> {
            paymentService.payment(orderId, paymentMethod, userId);
        });
    }

    @Test
    void cancelPayment_PaymentExists_UpdatesPaymentStatus() {
        Long orderId = 1L;
        String userId = "user123";

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setPaymentStatus("PENDING");

        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.cancelPayment(orderId, userId);

        assertNotNull(result);
        assertEquals("CANCELLED", result.getPaymentStatus());
    }

    @Test
    void cancelPayment_PaymentNotFound_ThrowsNotFoundException() {
        Long orderId = 1L;
        String userId = "user123";

        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            paymentService.cancelPayment(orderId, userId);
        });
    }

    @Test
    void cancelPayment_UserIdMismatch_ThrowsNotFoundException() {
        Long orderId = 1L;
        String userId = "user123";

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId("differentUser");

        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.of(payment));

        assertThrows(NotFoundException.class, () -> {
            paymentService.cancelPayment(orderId, userId);
        });
    }

    @Test
    void verifyPayment_OrderExistsAndPaymentExists_UpdatesPaymentStatusAndSendsNotification() {
        Long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setOrderItems(List.of(new OrderItem()));

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setPaymentStatus("PENDING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.verifyPayment(orderId, userId);

        assertNotNull(result);
        assertEquals("PAID", result.getPaymentStatus());
        assertNotNull(result.getPaidAt());

        ArgumentCaptor<NotificationDTO> notificationCaptor = ArgumentCaptor.forClass(NotificationDTO.class);
        verify(kafkaService).sendNotification(notificationCaptor.capture());
        NotificationDTO notification = notificationCaptor.getValue();
        assertEquals("Payment for order " + order.getId() + "and " + payment.getId() + " has been paid", notification.getMessage());
        assertEquals(userId, notification.getUserId());
        assertEquals(NotificationType.PAYMENT, notification.getType());
    }

    @Test
    void verifyPayment_OrderNotFound_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            paymentService.verifyPayment(orderId, userId);
        });
    }

    @Test
    void verifyPayment_UserIdMismatch_ThrowsNotFoundException() {
        Long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId("differentUser");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(NotFoundException.class, () -> {
            paymentService.verifyPayment(orderId, userId);
        });
    }

    @Test
    void verifyPayment_PaymentNotFound_ThrowsNotFoundException() {
        Long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            paymentService.verifyPayment(orderId, userId);
        });
    }


}
