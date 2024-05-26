package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CartTest {
    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId(1);
        cart.setUserId("orang");
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testId() {
        assertEquals(1, cart.getId());
    }

    @Test
    void testUserId() {
        assertEquals("orang", cart.getUserId());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        cart.setCreatedAt(now);
        assertEquals(now, cart.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        cart.setUpdatedAt(now);
        assertEquals(now, cart.getUpdatedAt());
    }

    @Test
    void testCartInitialization() {
        Cart newCart = new Cart();
        assertNotNull(newCart);
    }

    @AfterEach
    void tearDown() {
        cart = null;
    }
}
