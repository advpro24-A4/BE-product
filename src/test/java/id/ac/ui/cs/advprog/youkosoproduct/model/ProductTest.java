package id.ac.ui.cs.advprog.youkosoproduct.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {
    Product product;

    @BeforeEach
    void SetUp() {
        product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop");
        this.product.setProductPrice(2249000);
        this.product.setProductStock(5);
        this.product.setProductDiscount(20);
        this.product.setProductDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.");
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
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
