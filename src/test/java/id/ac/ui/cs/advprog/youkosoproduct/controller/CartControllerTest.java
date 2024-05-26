//package id.ac.ui.cs.advprog.youkosoproduct.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import id.ac.ui.cs.advprog.youkosoproduct.dto.*;
//import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
//import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
//import id.ac.ui.cs.advprog.youkosoproduct.model.User;
//import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
//import id.ac.ui.cs.advprog.youkosoproduct.service.ICartItemService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CartControllerTest {
//
//    private CartController cartController;
//
//    @Mock
//    private ICartItemService cartItemService;
//
//    @Mock
//    private AuthService authService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        cartController = new CartController(cartItemService, authService);
//    }
//
//    @Test
//    void givenValidAuthHeader_whenGetCart_thenReturnsCartItems() {
//        // Arrange
//        String authHeader = "Bearer validToken";
//        String userId = "user123";
//        List<CartItem> expectedCartItems = Collections.singletonList(new CartItem());
//
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters
//
//        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
//        when(cartItemService.findByUserId(userId)).thenReturn(expectedCartItems);
//
//        // Act
//        ResponseEntity<DefaultResponse<List<CartItem>>> responseEntity = cartController.getCart(authHeader);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals(expectedCartItems, responseEntity.getBody().getData());
//
//        // Verify interactions
//        verify(authService).validateToken(authHeader);
//        verify(cartItemService).findByUserId(userId);
//    }
//
//    @Test
//    void givenValidAuthHeaderAndRequest_whenAddProductToCart_thenAddsProductToCart() {
//        // Arrange
//        String authHeader = "Bearer validToken";
//        String userId = "user123";
//        AddCartRequest addCartRequest = new AddCartRequest();
//        addCartRequest.setProduct_id(123);
//        addCartRequest.setQuantity(2);
//
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters
//
//        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
//        when(cartItemService.addProductToCartItem(userId, addCartRequest.getProduct_id(), addCartRequest.getQuantity()))
//                .thenReturn(new CartItem());
//
//        // Act
//        ResponseEntity<DefaultResponse<CartItem>> responseEntity = cartController.addProductToCart(authHeader, addCartRequest);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertNotNull(responseEntity.getBody().getData());
//
//        // Verify interactions
//        verify(authService).validateToken(authHeader);
//        verify(cartItemService).addProductToCartItem(userId, addCartRequest.getProduct_id(), addCartRequest.getQuantity());
//    }
//
//    @Test
//    void givenValidAuthHeaderAndRequest_whenRemoveProductFromCart_thenRemovesProductFromCart() {
//        // Arrange
//        String authHeader = "Bearer validToken";
//        String userId = "user123";
//        int productId = 123; // Use the product id from the model
//
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters
//
//        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
//        when(cartItemService.removeProductFromCartItem(userId, productId)).thenReturn(new CartItem());
//
//        RemoveCartRequest removeCartRequest = new RemoveCartRequest();
//        removeCartRequest.setProduct_id(productId);
//
//        // Act
//        ResponseEntity<DefaultResponse<CartItem>> responseEntity = cartController.removeProductFromCart(authHeader, removeCartRequest);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertNotNull(responseEntity.getBody().getData());
//
//        // Verify interactions
//        verify(authService).validateToken(authHeader);
//        verify(cartItemService).removeProductFromCartItem(userId, productId);
//    }
//
//
//
//
//
//
//    // Add similar tests for other methods like removeProductFromCart and checkout
//}
