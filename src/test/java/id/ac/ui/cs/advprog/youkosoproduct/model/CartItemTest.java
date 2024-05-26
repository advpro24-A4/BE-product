package id.ac.ui.cs.advprog.youkosoproduct.model;

import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.CartItemBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {
    private final CartItemBuilder cartItemBuilder = new CartItemBuilder();
    private final ProductBuilder productBuilder = new ProductBuilder();
    private CartItem cartItem;
    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        cart = new Cart();

        product = productBuilder
                .productId(1)
                .productName("Baju")
                .productPrice(100)
                .productStock(10)
                .productDiscount(5)
                .productDescription("Deskripsih")
                .productImage("image_url")
                .build();

        cartItem = cartItemBuilder
                .id(1)
                .cart(cart)
                .product(product)
                .user("orang")
                .quantity(2)
                .price(200)
                .build();
    }

    @Test
    void testCartItemId() {
        assertEquals(1, cartItem.getId());
    }

    @Test
    void testCartItemCart() {
        assertEquals(cart, cartItem.getCart());
    }

    @Test
    void testCartItemProduct() {
        assertEquals(product, cartItem.getProduct());
    }

    @Test
    void testCartItemUserId() {
        assertEquals("orang", cartItem.getUserId());
    }

    @Test
    void testCartItemQuantity() {
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    void testCartItemPrice() {
        assertEquals(200, cartItem.getPrice());
    }

    @Test
    void testCartItemConstructor() {
        Cart cart = new Cart();
        Product product = new Product();

        String userId = "halow";
        int quantity = 2;
        int price = 200;

        CartItem cartItem = new CartItem(cart, product, userId, quantity, price);

        assertNotNull(cartItem);

        assertEquals(cart, cartItem.getCart());
        assertEquals(product, cartItem.getProduct());
        assertEquals(userId, cartItem.getUserId());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(price, cartItem.getPrice());

        assertNull(cartItem.getCreatedAt());
        assertNull(cartItem.getUpdatedAt());
    }

    @Test
    void testSetCreatedAt() {
        CartItem cartItem = new CartItem();
        LocalDateTime currentTime = LocalDateTime.now();

        cartItem.setCreatedAt(currentTime);
        assertEquals(currentTime, cartItem.getCreatedAt());
    }

    @Test
    void testSetUpdatedAt() {
        CartItem cartItem = new CartItem();
        LocalDateTime currentTime = LocalDateTime.now();

        cartItem.setUpdatedAt(currentTime);
        assertEquals(currentTime, cartItem.getUpdatedAt());
    }

    @AfterEach
    void tearDown() {
        cart = null;
    }
}
