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
        Cart existingCart = new Cart(); // Renaming the local variable to avoid hiding the field
        Product existingProduct = new Product(); // Renaming the local variable to avoid hiding the field
        String userId = "halow";
        int quantity = 2;
        int price = 200;
        CartItem existingCartItem = new CartItem(existingCart, existingProduct, userId, quantity, price); // Renaming the local variable to avoid hiding the field
        assertNotNull(existingCartItem);
        assertEquals(existingCart, existingCartItem.getCart()); // Adjusted to use the renamed variable
        assertEquals(existingProduct, existingCartItem.getProduct());
        assertEquals(userId, existingCartItem.getUserId());
        assertEquals(quantity, existingCartItem.getQuantity());
        assertEquals(price, existingCartItem.getPrice());
        assertNull(existingCartItem.getCreatedAt());
        assertNull(existingCartItem.getUpdatedAt());
    }

    @Test
    void testSetCreatedAt() {
        CartItem existingCartItem = new CartItem(); // Renamed the local variable to avoid hiding the field
        LocalDateTime currentTime = LocalDateTime.now();
        existingCartItem.setCreatedAt(currentTime);
        assertEquals(currentTime, existingCartItem.getCreatedAt());
    }

    @Test
    void testSetUpdatedAt() {
        CartItem existingCartItem = new CartItem(); // Renamed the local variable to avoid hiding the field
        LocalDateTime currentTime = LocalDateTime.now();
        existingCartItem.setUpdatedAt(currentTime);
        assertEquals(currentTime, existingCartItem.getUpdatedAt());
    }

    @AfterEach
    void tearDown() {
        cart = null;
    }
}
