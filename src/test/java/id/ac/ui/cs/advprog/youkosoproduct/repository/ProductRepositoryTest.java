package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        this.product = new ProductBuilder()
                .productName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop")
                .productPrice(2249000)
                .productStock(5)
                .productDiscount(20)
                .productDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.")
                .build();
    }
    @Test
    void testCreateUpdateProduct() {
        Product createdProduct = productRepository.save(product);

        assertNotNull(createdProduct);
        assertThat(createdProduct.getProductId()).isGreaterThan(0);

        createdProduct.setProductPrice(1000000);
        Product savedUpdatedProduct = productRepository.save(createdProduct);

        assertNotNull(savedUpdatedProduct);
        assertEquals(1000000, savedUpdatedProduct.getProductPrice());
    }

    @Test
    void testListAllProducts() {
        Iterable<Product> iterableProducts = productRepository.findAll();
        iterableProducts.forEach(System.out::println);
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Iterable<Product> productIterable = productRepository.findAll();
        Iterator<Product> productIterator = productIterable.iterator();
        assertTrue(productIterator.hasNext());
    }

    @Test
    void testGetProduct() {
        Integer productId = 49;
        Optional<Product> optionalProduct = productRepository.findById(productId);

        assertThat(optionalProduct).isPresent();
        optionalProduct.ifPresent(product -> assertThat(product).isNotNull());
    }

    @Test
    void testDeleteProduct() {
        String productName = "Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop";

        Optional<Product> optionalProduct = productRepository.findByProductName(productName);
        optionalProduct.ifPresent(product -> productRepository.delete(product));

        assertFalse(productRepository.findByProductName(productName).isPresent());
    }

}
