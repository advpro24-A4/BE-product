package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop");
        product1.setProductPrice(2249000);
        product1.setProductStock(5);
        product1.setProductDiscount(20);
        product1.setProductDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.");
        products.add(product1);

        Product product2 = new Product();
        product2.setProductName("Nendoroid Itoshi Rin - Blue Lock");
        product2.setProductPrice(680000);
        product2.setProductStock(10);
        product2.setProductDiscount(15);
        product2.setProductDescription("Crushing Itoshi Sae is everything to me in soccer.");
        products.add(product2);
    }

    @Test
    void testCreateProduct() {
        Product product = products.getFirst();
        Product createdProduct = productService.createProduct(product);

        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).createProduct(createdProduct);
    }

    @Test
    void testUpdateProduct() {
        Product product = products.get(1);
        Product updatedProduct = new Product();

        updatedProduct.setProductStock(20);
        updatedProduct.setProductPrice(30000);

        when(productRepository.updateProduct(product.getProductId(), updatedProduct)).thenReturn(updatedProduct);
        Product result = productService.updateProduct(product.getProductId(), updatedProduct);

        verify(productRepository, times(1)).updateProduct(product.getProductId(), updatedProduct);
        assertEquals(updatedProduct, result);
    }

    @Test
    void testFindAllProducts() {
        Product product = products.getFirst();
        when(productRepository.createProduct(product)).thenReturn(product);
        productService.createProduct(product);

        Iterator<Product> productIterator = products.iterator();

        when(productRepository.findAllProduct()).thenReturn(productIterator);
        List<Product> foundProducts = productService.findAllProduct();

        assertEquals(product, foundProducts.getFirst());
        verify(productRepository, times(1)).findAllProduct();
    }

    @Test
    void testFindProductById() {
        Product product = products.getFirst();
        String productId = product.getProductId();
        when(productRepository.findProductById(productId)).thenReturn(product);

        Product foundProduct = productService.findProductById(productId);

        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findProductById(productId);
    }


    @Test
    void testDeleteProduct() {
        Product product = new Product();
        String productId = product.getProductId();
        when(productRepository.deleteProduct(productId)).thenReturn(product);

        Product deletedProduct = productService.deleteProduct(productId);

        assertEquals(product, deletedProduct);
        verify(productRepository, times(1)).deleteProduct(productId);
    }
}