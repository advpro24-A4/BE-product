package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.PaymentRequest;
import id.ac.ui.cs.advprog.youkosoproduct.dto.VerifyDeletePaymentRequest;
import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;
import id.ac.ui.cs.advprog.youkosoproduct.model.User;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.IPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    private PaymentController paymentController;

    @Mock
    private IPaymentService paymentService;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentController = new PaymentController(paymentService, authService);
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenCreatePayment_thenCreatesPayment() {
        // Arrange
        String authHeader = "Bearer validToken";
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(123L);
        paymentRequest.setPaymentMethod("method");

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User("user123", "test@example.com", "role", null));

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(paymentService.payment(paymentRequest.getOrderId(), paymentRequest.getPaymentMethod(), authResponse.getUser().getId()))
                .thenReturn(new Order());

        // Act
        ResponseEntity<DefaultResponse<Order>> responseEntity = paymentController.createPayment(authHeader, paymentRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService).payment(paymentRequest.getOrderId(), paymentRequest.getPaymentMethod(), authResponse.getUser().getId());
    }

    @Test
    void givenInvalidAuthHeader_whenCreatePayment_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "InvalidToken";

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<Order>> responseEntity = paymentController.createPayment(authHeader, new PaymentRequest());

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService, never()).payment((long) anyInt(), anyString(), anyString());
    }

    @Test
    void givenInvalidRequest_whenCreatePayment_thenThrowsBadRequestException() {
        // Arrange
        String authHeader = "Bearer validToken";
        PaymentRequest paymentRequest = new PaymentRequest(); // Missing order_id and payment_method

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User("user123", "test@example.com", "role", null));

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> paymentController.createPayment(authHeader, paymentRequest));

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService, never()).payment((long) anyInt(), anyString(), anyString());
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenCancelPayment_thenCancelsPayment() {
        // Arrange
        String authHeader = "Bearer validToken";
        VerifyDeletePaymentRequest verifyDeletePaymentRequest = new VerifyDeletePaymentRequest();
        verifyDeletePaymentRequest.setOrderId(123L);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User("user123", "test@example.com", "role", null));

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(paymentService.cancelPayment(verifyDeletePaymentRequest.getOrderId(), authResponse.getUser().getId()))
                .thenReturn(new Payment());

        // Act
        ResponseEntity<DefaultResponse<Payment>> responseEntity = paymentController.cancelPayment(authHeader, verifyDeletePaymentRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService).cancelPayment(verifyDeletePaymentRequest.getOrderId(), authResponse.getUser().getId());
    }

    @Test
    void givenInvalidAuthHeader_whenCancelPayment_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "InvalidToken";

        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<Payment>> responseEntity = paymentController.cancelPayment(authHeader, new VerifyDeletePaymentRequest());

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService, never()).cancelPayment((long) anyInt(), anyString());
    }

    @Test
    void givenValidAuthHeaderAndRequest_whenConfirmPayment_thenReturnsPaymentConfirmation() {
        // Arrange
        String authHeader = "Bearer validToken";
        VerifyDeletePaymentRequest verifyDeletePaymentRequest = new VerifyDeletePaymentRequest();
        verifyDeletePaymentRequest.setOrderId(123L); // Assuming the order ID is of type long

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(new User("user123", "test@example.com", "role", null));

        Payment mockPayment = new Payment();
        // Assuming the paymentService returns a valid payment
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(authResponse));
        when(paymentService.verifyPayment(anyLong(), anyString())).thenReturn(mockPayment); // Use anyLong() instead of anyInt()

        // Act
        ResponseEntity<DefaultResponse<Payment>> responseEntity = paymentController.confirmPayment(authHeader, verifyDeletePaymentRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());
        assertEquals(mockPayment, responseEntity.getBody().getData());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService).verifyPayment(verifyDeletePaymentRequest.getOrderId(), authResponse.getUser().getId());
    }


    @Test
    void givenNullAuthHeader_whenConfirmPayment_thenReturnsUnauthorized() {
        // Arrange
        String authHeader = "Bearer validToken";
        VerifyDeletePaymentRequest verifyDeletePaymentRequest = new VerifyDeletePaymentRequest();
        verifyDeletePaymentRequest.setOrderId(123L);

        // Simulate null authResponse
        when(authService.validateToken(authHeader)).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        ResponseEntity<DefaultResponse<Payment>> responseEntity = paymentController.confirmPayment(authHeader, verifyDeletePaymentRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify interactions
        verify(authService).validateToken(authHeader);
        verify(paymentService, never()).verifyPayment((long) anyInt(), anyString());
    }
    // Add similar tests for cancelPayment and confirmPayment methods
}
