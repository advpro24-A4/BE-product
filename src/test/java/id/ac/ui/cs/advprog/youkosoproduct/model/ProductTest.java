package id.ac.ui.cs.advprog.youkosoproduct.model;

import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {
    Product product;

    @BeforeEach
    void SetUp() {
        this.product = new ProductBuilder()
                .productId(12345)
                .productName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop")
                .productPrice(2249000)
                .productStock(5)
                .productDiscount(20)
                .productDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.")
                .build();
    }

    @Test
    void testGetProductId() {
        assertEquals(12345, this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop", this.product.getProductName());
    }

    @Test
    void testGetProductPrice() {
        assertEquals(2249000, this.product.getProductPrice());
    }

    @Test
    void testGetProductStock() {
        assertEquals(5, this.product.getProductStock());
    }

    @Test
    void testGetProductDiscount() {
        assertEquals(20, this.product.getProductDiscount());
    }

    @Test
    void testGetProductDescription() {
        assertEquals("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.", this.product.getProductDescription());
    }
}
