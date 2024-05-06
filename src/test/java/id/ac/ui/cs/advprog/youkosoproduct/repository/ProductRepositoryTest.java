package id.ac.ui.cs.advprog.youkosoproduct.repository;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createFindUpdateProduct() {
        Product product = new Product();
        product.setProductName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop");
        product.setProductPrice(2249000);
        product.setProductStock(5);
        product.setProductDiscount(20);
        product.setProductDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.");

        Product createdProduct = productRepository.createProduct(product);

        Iterator<Product> productIterator = productRepository.findAllProduct();
        assertTrue(productIterator.hasNext());

        Product foundProduct = productRepository.findProductById(createdProduct.getProductId());
        assertEquals(createdProduct, foundProduct);

        Product updatedProduct = productRepository.findProductById(createdProduct.getProductId());
        updatedProduct.setProductName("Marvel Bishoujo Figure 1/3 Hawkeye / Kylie Bishop");
        updatedProduct.setProductPrice(1140000);
        Product updated = productRepository.updateProduct(createdProduct.getProductId(), updatedProduct);
        assertEquals(updated.getProductName(), "Marvel Bishoujo Figure 1/3 Hawkeye / Kylie Bishop");
        assertEquals(updated.getProductPrice(), 1140000);
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop");
        product1.setProductPrice(2249000);
        product1.setProductStock(5);
        product1.setProductDiscount(20);
        product1.setProductDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.");
        productRepository.createProduct(product1);

        Product product2 = new Product();
        product2.setProductName("Nendoroid Itoshi Rin - Blue Lock");
        product2.setProductPrice(680000);
        product2.setProductStock(10);
        product2.setProductDiscount(15);
        product2.setProductDescription("Crushing Itoshi Sae is everything to me in soccer.");
        productRepository.createProduct(product2);

        Iterator<Product> productIterator = productRepository.findAllProduct();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }


    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAllProduct();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop");
        product.setProductPrice(2249000);
        product.setProductStock(5);
        product.setProductDiscount(20);
        product.setProductDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.");
        productRepository.createProduct(product);

        Product deletedProduct = productRepository.deleteProduct(product.getProductId());
        assertEquals(product, deletedProduct);

        Iterator<Product> productIterator = productRepository.findAllProduct();
        assertFalse(productIterator.hasNext());
    }
}
