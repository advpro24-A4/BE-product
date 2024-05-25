//package id.ac.ui.cs.advprog.youkosoproduct.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ProductTest {
//    private final ProductBuilder productBuilder = new ProductBuilder();
//    private Product product;
//
//    @BeforeEach
//    void setUp() {
//        product = productBuilder
//                .productId(1)
//                .productName("Baju")
//                .productPrice(100)
//                .productStock(10)
//                .productDiscount(5)
//                .productDescription("Deskripsih")
//                .productImage("image_url")
//                .build();
//    }
//
//    @Test
//    void testProductId() {
//        assertEquals(1, product.getId());
//    }
//
//    @Test
//    void testProductName() {
//        assertEquals("Baju", product.getProductName());
//    }
//
//    @Test
//    void testProductPrice() {
//        assertEquals(100, product.getProductPrice());
//    }
//
//    @Test
//    void testProductStock() {
//        assertEquals(10, product.getProductStock());
//    }
//
//    @Test
//    void testProductDiscount() {
//        assertEquals(5, product.getProductDiscount());
//    }
//
//    @Test
//    void testProductDescription() {
//        assertEquals("Deskripsih", product.getProductDescription());
//    }
//
//    @Test
//    void testProductImage() {
//        assertEquals("image_url", product.getProductImage());
//    }
//}
