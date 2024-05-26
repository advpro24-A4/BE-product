package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.dto.NotificationDTO;
import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.enumaration.NotificationType;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private IOrderRepository orderRepository;
    private KafkaService kafkaService;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(IOrderRepository.class);
        kafkaService = mock(KafkaService.class);
        orderService = new OrderServiceImpl(orderRepository, kafkaService);
    }

    @Test
    void cancelOrder_OrderExistsAndUserIdMatches_CancelsOrderAndSendsNotification() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus("PENDING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.cancelOrder(orderId, userId);

        assertNotNull(result);
        assertEquals("CANCELLED", result.getStatus());

        ArgumentCaptor<NotificationDTO> notificationCaptor = ArgumentCaptor.forClass(NotificationDTO.class);
        verify(kafkaService).sendNotification(notificationCaptor.capture());
        NotificationDTO notification = notificationCaptor.getValue();
        assertEquals("Order with ID " + order.getId() + " has been cancelled", notification.getMessage());
        assertEquals(userId, notification.getUserId());
        assertEquals(NotificationType.ORDER, notification.getType());
    }

    @Test
    void cancelOrder_OrderNotFound_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.cancelOrder(orderId, userId));
    }

    @Test
    void cancelOrder_UserIdMismatch_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId("differentUser");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(NotFoundException.class, () -> orderService.cancelOrder(orderId, userId));
    }

    @Test
    void finishOrder_OrderExistsAndUserIdMatches_FinishesOrderAndSendsNotification() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus("DELIVERED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.finishOrder(orderId, userId);

        assertNotNull(result);
        assertEquals("FINISHED", result.getStatus());

        ArgumentCaptor<NotificationDTO> notificationCaptor = ArgumentCaptor.forClass(NotificationDTO.class);
        verify(kafkaService).sendNotification(notificationCaptor.capture());
        NotificationDTO notification = notificationCaptor.getValue();
        assertEquals("Order with ID " + order.getId() + " has been finished", notification.getMessage());
        assertEquals(userId, notification.getUserId());
        assertEquals(NotificationType.ORDER, notification.getType());
    }

    @Test
    void finishOrder_OrderNotFound_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.finishOrder(orderId, userId));
    }

    @Test
    void finishOrder_UserIdMismatch_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId("differentUser");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(NotFoundException.class, () -> orderService.finishOrder(orderId, userId));
    }

    @Test
    void finishOrder_OrderAlreadyFinished_ThrowsBadRequestException() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus("FINISHED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(BadRequestException.class, () -> orderService.finishOrder(orderId, userId));
    }

    @Test
    void finishOrder_OrderAlreadyCancelled_ThrowsBadRequestException() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus("CANCELLED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(BadRequestException.class, () -> orderService.finishOrder(orderId, userId));
    }

    @Test
    void finishOrder_OrderNotDelivered_ThrowsBadRequestException() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus("PENDING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(BadRequestException.class, () -> orderService.finishOrder(orderId, userId));
    }

    @Test
    void getOrders_UserIdExists_ReturnsOrderList() {
        String userId = "user123";

        Order order1 = new Order();
        order1.setId(1L);
        order1.setUserId(userId);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setUserId(userId);

        List<Order> orders = List.of(order1, order2);

        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        List<Order> result = orderService.getOrders(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(userId, result.get(1).getUserId());
    }

    @Test
    void getOrders_UserIdDoesNotExist_ReturnsEmptyList() {
        String userId = "user123";

        when(orderRepository.findByUserId(userId)).thenReturn(List.of());

        List<Order> result = orderService.getOrders(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getOrder_OrderExistsAndUserIdMatches_ReturnsOrder() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(orderId, userId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(userId, result.getUserId());
    }

    @Test
    void getOrder_OrderNotFound_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.getOrder(orderId, userId));
    }

    @Test
    void getOrder_UserIdMismatch_ThrowsNotFoundException() {
        long orderId = 1L;
        String userId = "user123";

        Order order = new Order();
        order.setId(orderId);
        order.setUserId("differentUser");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(NotFoundException.class, () -> orderService.getOrder(orderId, userId));
    }
}
