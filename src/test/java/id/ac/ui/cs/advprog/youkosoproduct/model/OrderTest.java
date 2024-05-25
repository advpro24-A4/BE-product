//package id.ac.ui.cs.advprog.youkosoproduct.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class OrderTest {
//    private Order order;
//    private Shipment shipment;
//    private Payment payment;
//    private OrderItem orderItem;
//
//    @BeforeEach
//    void setUp() {
//        order = new Order();
//        order.setId(1L);
//        order.setUserId("user123");
//        order.setCreatedAt(LocalDateTime.now());
//        order.setUpdatedAt(LocalDateTime.now());
//        order.setStatus("Processing");
//        order.setAlamatPengiriman("123 Main St");
//        order.setNamaPenerima("John Doe");
//        order.setNoHpPenerima("08123456789");
//
//        shipment = new Shipment(1L, "JTE", "JTE-123456789012", "Shipped");
//        order.setShipment(shipment);
//
//        payment = new Payment();
//        payment.setId(1L);
//        payment.setOrderId(1L);
//        payment.setUserId("user123");
//        payment.setTotalPrice(new BigDecimal("1000.00"));
//        payment.setPaymentStatus("Paid");
//        payment.setCreatedAt(LocalDateTime.now());
//        payment.setUpdatedAt(LocalDateTime.now());
//        payment.setPaidAt(LocalDateTime.now());
//        order.setPayment(payment);
//
//        orderItem = new OrderItem();
//        orderItem.setId(1L);
//        orderItem.setOrder(order);
//        orderItem.setProduct(new Product());
//        orderItem.setQuantity(2);
//        orderItem.setPrice(200.0);
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(orderItem);
//        order.setOrderItems(orderItems);
//    }
//
//    @Test
//    void testOrderId() {
//        assertEquals(1L, order.getId());
//    }
//
//    @Test
//    void testUserId() {
//        assertEquals("user123", order.getUserId());
//    }
//
//    @Test
//    void testCreatedAt() {
//        LocalDateTime now = LocalDateTime.now();
//        order.setCreatedAt(now);
//        assertEquals(now, order.getCreatedAt());
//    }
//
//    @Test
//    void testUpdatedAt() {
//        LocalDateTime now = LocalDateTime.now();
//        order.setUpdatedAt(now);
//        assertEquals(now, order.getUpdatedAt());
//    }
//
//    @Test
//    void testStatus() {
//        assertEquals("Processing", order.getStatus());
//    }
//
//    @Test
//    void testAlamatPengiriman() {
//        assertEquals("123 Main St", order.getAlamatPengiriman());
//    }
//
//    @Test
//    void testNamaPenerima() {
//        assertEquals("John Doe", order.getNamaPenerima());
//    }
//
//    @Test
//    void testNoHpPenerima() {
//        assertEquals("08123456789", order.getNoHpPenerima());
//    }
//
//    @Test
//    void testShipment() {
//        assertNotNull(order.getShipment());
//        assertEquals(1L, order.getShipment().getOrderId());
//        assertEquals("JTE", order.getShipment().getShipmentMethod());
//        assertEquals("JTE-123456789012", order.getShipment().getTrackingNumber());
//        assertEquals("Shipped", order.getShipment().getShipmentStatus());
//    }
//
//    @Test
//    void testPayment() {
//        assertNotNull(order.getPayment());
//        assertEquals(1L, order.getPayment().getId());
//        assertEquals(1L, order.getPayment().getOrderId());
//        assertEquals("user123", order.getPayment().getUserId());
//        assertEquals(new BigDecimal("1000.00"), order.getPayment().getTotalPrice());
//        assertEquals("Paid", order.getPayment().getPaymentStatus());
//    }
//
//    @Test
//    void testOrderItems() {
//        assertNotNull(order.getOrderItems());
//        assertEquals(1, order.getOrderItems().size());
//        assertEquals(orderItem, order.getOrderItems().get(0));
//    }
//
//    @AfterEach
//    void tearDown() {
//        order = null;
//        shipment = null;
//        payment = null;
//        orderItem = null;
//    }
//}
