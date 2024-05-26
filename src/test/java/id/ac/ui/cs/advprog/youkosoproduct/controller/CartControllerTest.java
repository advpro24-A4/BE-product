package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.*;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.User;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.ICartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    private CartController cartController;

    @Mock
    private ICartItemService cartItemService;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cartController = new CartController(cartItemService, authService);
    }

    @Test
    void givenValidAuthHeader_whenGetCart_thenReturnsCartItems() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        List<CartItem> expectedCartItems = Collections.singletonList(new CartItem());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(cartItemService.findByUserId(userId)).thenReturn(expectedCartItems);

        // Act
        ResponseEntity<DefaultResponse<List<CartItem>>> responseEntity = cartController.getCart(authHeader);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedCartItems, responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(cartItemService).findByUserId(userId);
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenAddProductToCart_thenAddsProductToCart() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        AddCartRequest addCartRequest = new AddCartRequest();
        addCartRequest.setProduct_id(123);
        addCartRequest.setQuantity(2);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(cartItemService.addProductToCartItem(userId, addCartRequest.getProduct_id(), addCartRequest.getQuantity()))
                .thenReturn(new CartItem());

        // Act
        ResponseEntity<DefaultResponse<CartItem>> responseEntity = cartController.addProductToCart(authHeader, addCartRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(cartItemService).addProductToCartItem(userId, addCartRequest.getProduct_id(), addCartRequest.getQuantity());
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenRemoveProductFromCart_thenRemovesProductFromCart() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        int productId = 123; // Use the product id from the model

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters

        // Add the logic for authResponse validation
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(cartItemService.removeProductFromCartItem(userId, productId)).thenReturn(new CartItem());

        RemoveCartRequest removeCartRequest = new RemoveCartRequest();
        removeCartRequest.setProduct_id(productId);

        // Act
        ResponseEntity<DefaultResponse<CartItem>> responseEntity = cartController.removeProductFromCart(authHeader, removeCartRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(cartItemService).removeProductFromCartItem(userId, productId);
    }

    @Test
    void givenNullAuthResponse_whenAddProductToCart_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "Bearer validToken";
        AddCartRequest addCartRequest = new AddCartRequest();
        addCartRequest.setProduct_id(123);
        addCartRequest.setQuantity(2);

        // Simulate null authResponse
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<CartItem>> responseEntity = cartController.addProductToCart(authHeader, addCartRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(cartItemService, never()).addProductToCartItem(anyString(), anyInt(), anyInt()); // Ensure addProductToCartItem method is not called
    }

    @Test
    void givenNullAuthResponse_whenCheckout_thenReturnsUnauthorized() {
        String authHeader = "Bearer validToken";
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setRecipient_address("address");
        checkoutRequest.setRecipient_name("name");
        checkoutRequest.setRecipient_phone_number("phone");

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<DefaultResponse<Order>> responseEntity = cartController.checkout(authHeader, checkoutRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(authService).validateToken(authHeader);
        verify(cartItemService, never()).checkout(any(), any(), any(), any());
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenCheckout_thenProcessesCheckout() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setRecipient_address("address");
        checkoutRequest.setRecipient_name("name");
        checkoutRequest.setRecipient_phone_number("phone");

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null)); // Use the User constructor with parameters

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));

        Order mockOrder = new Order();
        when(cartItemService.checkout(userId, checkoutRequest.getRecipient_address(), checkoutRequest.getRecipient_name(), checkoutRequest.getRecipient_phone_number()))
                .thenReturn(mockOrder);

        ResponseEntity<DefaultResponse<Order>> responseEntity = cartController.checkout(authHeader, checkoutRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());
        assertEquals(mockOrder, responseEntity.getBody().getData()); // Assert that the returned order matches the mocked order

        verify(authService).validateToken(authHeader);
        verify(cartItemService).checkout(userId, checkoutRequest.getRecipient_address(), checkoutRequest.getRecipient_name(), checkoutRequest.getRecipient_phone_number());
    }
    @Test
    void givenNullAuthResponse_whenGetCart_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "Bearer validToken";

        // Simulate null authResponse
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<List<CartItem>>> responseEntity = cartController.getCart(authHeader);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(cartItemService, never()).findByUserId(any());
    }

    @Test
    void givenNullAuthResponse_whenRemoveProductFromCart_thenReturnsUnauthorized() {
        String authHeader = "Bearer validToken";
        int productId = 123;

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        RemoveCartRequest removeCartRequest = new RemoveCartRequest();
        removeCartRequest.setProduct_id(productId);

        ResponseEntity<DefaultResponse<CartItem>> responseEntity = cartController.removeProductFromCart(authHeader, removeCartRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        verify(authService).validateToken(authHeader);
        verify(cartItemService, never()).removeProductFromCartItem(anyString(), anyInt()); // Ensure removeProductFromCartItem method is not called
    }


}
