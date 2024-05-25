package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.OrderItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IOrderRepository;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final IOrderRepository orderRepository;
    private final IPaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(IOrderRepository orderRepository, IPaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Order payment(Long orderId, String paymentMethod, String userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found");
        }

        Payment currPayment = paymentRepository.findByOrderId(orderId).orElse(null);

        if (currPayment != null) {
            if (currPayment.getPaymentStatus().equals("PENDING")) {
                throw new BadRequestException("Payment already exists");
            }
        }

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setUserId(order.getUserId());
        payment.setPaymentMethod(paymentMethod);
        payment.setTotalPrice(order.getOrderItems().stream().mapToLong(OrderItem::getPrice).sum());
        payment.setPaymentStatus("PENDING");
        payment = paymentRepository.save(payment);
        order.setPayment(payment);
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public Payment cancelPayment(Long orderId, String userId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("Payment not found"));

        if (!payment.getUserId().equals(userId)) {
            throw new NotFoundException("Payment not found");
        }

        payment.setPaymentStatus("CANCELLED");
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment verifyPayment(Long orderId, String userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found");
        }


        LocalDateTime now = LocalDateTime.now();
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("Payment not found"));
        payment.setPaymentStatus("PAID");
        payment.setPaidAt(now);
        paymentRepository.save(payment);
        order.setStatus("WAITING_PAYMENT_CONFIRMATION");
        orderRepository.save(order);
        return payment;
    }
}