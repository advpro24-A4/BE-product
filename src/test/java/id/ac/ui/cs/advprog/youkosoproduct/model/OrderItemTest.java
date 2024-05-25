package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderItemTest {
    private OrderItem orderItem;
    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        order = new Order();
        product = new Product();

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(10);
        orderItem.setPrice(100);
    }

    @Test
    void testId() {
        assertEquals(1L, orderItem.getId());
    }

    @Test
    void testOrder() {
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    void testProduct() {
        assertEquals(product, orderItem.getProduct());
    }

    @Test
    void testQuantity() {
        assertEquals(10, orderItem.getQuantity());
    }

    @Test
    void testPrice() {
        assertEquals(100.0, orderItem.getPrice());
    }

    @Test
    void testOrderItemInitialization() {
        OrderItem newOrderItem = new OrderItem();
        assertNotNull(newOrderItem);
    }

    @AfterEach
    void tearDown() {
        orderItem = null;
    }
}
