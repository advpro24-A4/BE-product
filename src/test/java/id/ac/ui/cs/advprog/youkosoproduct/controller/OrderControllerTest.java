package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DeleteRequest;
import id.ac.ui.cs.advprog.youkosoproduct.dto.FinishRequest;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.User;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.IOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderController orderController;

    @Mock
    private IOrderService orderService;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(orderService, authService);
    }

    @Test
    void givenValidAuthHeader_whenGetOrders_thenReturnsOrders() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        List<Order> expectedOrders = Collections.singletonList(new Order());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null));

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(orderService.getOrders(userId)).thenReturn(expectedOrders);

        // Act
        ResponseEntity<DefaultResponse<List<Order>>> responseEntity = orderController.getOrders(authHeader);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedOrders, responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(orderService).getOrders(userId);
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenCancelOrder_thenCancelsOrder() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        long orderId = 123;

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null));

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(orderService.cancelOrder(orderId, userId)).thenReturn(new Order());

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setOrder_id(orderId);

        // Act
        ResponseEntity<DefaultResponse<Order>> responseEntity = orderController.cancelOrder(authHeader, deleteRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(orderService).cancelOrder(orderId, userId);
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenCreateOrder_thenCreatesOrder() {
        // Arrange
        String authHeader = "Bearer validToken";
        String userId = "user123";
        long orderId = 123;

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User(userId, "test@example.com", "role", null));

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(orderService.finishOrder(orderId, userId)).thenReturn(new Order());

        FinishRequest finishRequest = new FinishRequest();
        finishRequest.setOrder_id(orderId);

        // Act
        ResponseEntity<DefaultResponse<Order>> responseEntity = orderController.createOrder(authHeader, finishRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(orderService).finishOrder(orderId, userId);
    }

    @Test
    void givenNullAuthResponse_whenGetOrders_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "Bearer validToken";

        // Simulate null authResponse
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<List<Order>>> responseEntity = orderController.getOrders(authHeader);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(orderService, never()).getOrders(any());
    }

    @Test
    void givenNullAuthResponse_whenCancelOrder_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "Bearer validToken";
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setOrder_id(123L);

        // Simulate null authResponse
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<Order>> responseEntity = orderController.cancelOrder(authHeader, deleteRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(orderService, never()).cancelOrder(anyLong(), anyString());
    }

    @Test
    void givenNullAuthResponse_whenCreateOrder_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "Bearer validToken";
        FinishRequest finishRequest = new FinishRequest();
        finishRequest.setOrder_id(123L);

        // Simulate null authResponse
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<Order>> responseEntity = orderController.createOrder(authHeader, finishRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(orderService, never()).finishOrder(anyLong(), anyString());
    }

}
