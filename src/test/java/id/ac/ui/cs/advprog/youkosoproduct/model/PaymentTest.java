package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentTest {
    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(1L);
        payment.setUserId("orang");
        payment.setTotalPrice(1000);
        payment.setPaymentStatus("Paid");
        payment.setPaymentMethod("Credit Card");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        payment.setPaidAt(LocalDateTime.now());
    }

    @Test
    void testId() {
        assertEquals(1L, payment.getId());
    }

    @Test
    void testOrderId() {
        assertEquals(1L, payment.getOrderId());
    }

    @Test
    void testUserId() {
        assertEquals("orang", payment.getUserId());
    }

    @Test
    void testTotalPrice() {
        assertEquals(1000, payment.getTotalPrice());
    }

    @Test
    void testPaymentStatus() {
        assertEquals("Paid", payment.getPaymentStatus());
    }

    @Test
    void testPaymentMethod() {
        assertEquals("Credit Card", payment.getPaymentMethod());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        payment.setCreatedAt(now);
        assertEquals(now, payment.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        payment.setUpdatedAt(now);
        assertEquals(now, payment.getUpdatedAt());
    }

    @Test
    void testPaidAt() {
        LocalDateTime now = LocalDateTime.now();
        payment.setPaidAt(now);
        assertEquals(now, payment.getPaidAt());
    }

    @Test
    void testPaymentInitialization() {
        Payment newPayment = new Payment();
        assertNotNull(newPayment);
    }

    @AfterEach
    void tearDown() {
        payment = null;
    }
}
