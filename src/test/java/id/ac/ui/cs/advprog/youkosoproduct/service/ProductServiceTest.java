package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        // Arrange
        Product product1 = new Product(10);
        Product product2 = new Product(20);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void getAllProducts_ReturnsEmptyList_WhenNoProductsFound() {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of());

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(0, actualProducts.size());
    }

    @Test
    void getAllProducts_ReturnsNull_WhenRepositoryReturnsNull() {
        // Arrange
        when(productRepository.findAll()).thenReturn(null);

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertNull(actualProducts);
    }

    @Test
    void getAllProducts_CallsFindAllMethodOnce() {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of());

        // Act
        productService.getAllProducts();

        // Assert
        verify(productRepository, times(1)).findAll();
    }
}
