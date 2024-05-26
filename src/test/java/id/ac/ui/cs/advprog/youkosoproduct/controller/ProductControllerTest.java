package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private IProductService productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        // Arrange
        Product product1 = new Product(10);
        Product product2 = new Product(20);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(expectedProducts);

        // Act
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProducts, responseEntity.getBody());
    }

    @Test
    void getAllProducts_ReturnsEmptyList_WhenNoProductsFound() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(List.of());

        // Act
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).size());
    }

}
