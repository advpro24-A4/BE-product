//package id.ac.ui.cs.advprog.youkosoproduct.repository;
//
//import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
//import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//@ExtendWith(MockitoExtension.class)
//public class ProductRepositoryTest {
//    @Mock
//    private ProductRepository productRepository;
//
//    Product product;
//
//    @BeforeEach
//    void setUp() {
//        this.product = new ProductBuilder()
//                .productId(1)
//                .productName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop")
//                .productPrice(2249000)
//                .productStock(5)
//                .productDiscount(20)
//                .productDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.")
//                .productImage("/images/image11.jpg")
//                .build();
//    }
//    @Test
//    void testCreateUpdateProduct() {
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        Product createdProduct = productRepository.save(product);
//        assertNotNull(createdProduct);
//        assertThat(createdProduct.getProductId()).isGreaterThan(0);
//
//        createdProduct.setProductPrice(1000000);
//        when(productRepository.save(createdProduct)).thenReturn(createdProduct);
//
//        Product savedUpdatedProduct = productRepository.save(createdProduct);
//        assertNotNull(savedUpdatedProduct);
//        assertEquals(1000000, savedUpdatedProduct.getProductPrice());
//    }
//
//    @Test
//    void testListAllProducts() {
//        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
//
//        Iterable<Product> iterableProducts = productRepository.findAll();
//        assertNotNull(iterableProducts);
//
//        Iterator<Product> iterator = iterableProducts.iterator();
//        assertTrue(iterator.hasNext());
//        assertEquals(product, iterator.next());
//    }
//
//    @Test
//    void testGetProduct() {
//        Integer productId = 100;
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        Optional<Product> optionalProduct = productRepository.findById(productId);
//        assertThat(optionalProduct).isPresent();
//        optionalProduct.ifPresent(p -> assertThat(p).isEqualTo(product));
//    }
//
//    @Test
//    void testDeleteProduct() {
//        String productName = "Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop";
//
//        when(productRepository.findByProductName(productName)).thenReturn(Optional.of(product));
//
//        Optional<Product> optionalProduct = productRepository.findByProductName(productName);
//        assertTrue(optionalProduct.isPresent());
//
//        Product productToDelete = optionalProduct.get();
//        productRepository.delete(productToDelete);
//        verify(productRepository).delete(productToDelete);
//
//        when(productRepository.findByProductName(productName)).thenReturn(Optional.empty());
//        assertFalse(productRepository.findByProductName(productName).isPresent());
//    }
//}
