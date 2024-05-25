//package id.ac.ui.cs.advprog.youkosoproduct.model;
//
//import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.CartItemBuilder;
//import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CartItemTest {
//    private final CartItemBuilder cartItemBuilder = new CartItemBuilder();
//    private final ProductBuilder productBuilder = new ProductBuilder();
//    private CartItem cartItem;
//    private Cart cart;
//    private Product product;
//
//    @BeforeEach
//    void setUp() {
//        cart = new Cart();
//
//        product = productBuilder
//                .productId(1)
//                .productName("Baju")
//                .productPrice(100)
//                .productStock(10)
//                .productDiscount(5)
//                .productDescription("Deskripsih")
//                .productImage("image_url")
//                .build();
//
//        cartItem = cartItemBuilder
//                .id(1)
//                .cart(cart)
//                .product(product)
//                .user("orang")
//                .quantity(2)
//                .price(200)
//                .build();
//    }
//
//    @Test
//    void testCartItemId() {
//        assertEquals(1, cartItem.getId());
//    }
//
//    @Test
//    void testCartItemCart() {
//        assertEquals(cart, cartItem.getCart());
//    }
//
//    @Test
//    void testCartItemProduct() {
//        assertEquals(product, cartItem.getProduct());
//    }
//
//    @Test
//    void testCartItemUserId() {
//        assertEquals("orang", cartItem.getUserId());
//    }
//
//    @Test
//    void testCartItemQuantity() {
//        assertEquals(2, cartItem.getQuantity());
//    }
//
//    @Test
//    void testCartItemPrice() {
//        assertEquals(200, cartItem.getPrice());
//    }
//}
