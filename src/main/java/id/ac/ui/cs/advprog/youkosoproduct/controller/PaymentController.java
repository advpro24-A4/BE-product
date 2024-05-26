package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.PaymentRequest;
import id.ac.ui.cs.advprog.youkosoproduct.dto.VerifyDeletePaymentRequest;
import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.DefaultResponseBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final IPaymentService paymentService;
    private final AuthService authService;

    @Autowired
    public PaymentController(IPaymentService paymentService, AuthService authService) {
        this.paymentService = paymentService;
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<DefaultResponse<Order>> createPayment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody PaymentRequest paymentRequest
    ) {
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (paymentRequest.getOrderId() == null || paymentRequest.getPaymentMethod() == null) {
            throw new BadRequestException("Invalid request");
        }

        Order order = paymentService.payment(paymentRequest.getOrderId(), paymentRequest.getPaymentMethod(), authResponse.getUser().getId());
        DefaultResponse<Order> response = new DefaultResponseBuilder<Order>().statusCode(HttpStatus.OK.value()).message("Success create payment").success(true).data(order).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<DefaultResponse<Payment>> cancelPayment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody VerifyDeletePaymentRequest verifyDeletePaymentRequest
    ) {
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Payment payment = paymentService.cancelPayment(verifyDeletePaymentRequest.getOrderId(), authResponse.getUser().getId());
        DefaultResponse<Payment> response = new DefaultResponseBuilder<Payment>().statusCode(HttpStatus.OK.value()).message("Success cancel payment").success(true).data(payment).build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify")
    public ResponseEntity<DefaultResponse<Payment>> confirmPayment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody VerifyDeletePaymentRequest verifyDeletePaymentRequest
    ) {
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Payment payment = paymentService.verifyPayment(verifyDeletePaymentRequest.getOrderId(), authResponse.getUser().getId());
        DefaultResponse<Payment> response = new DefaultResponseBuilder<Payment>().statusCode(HttpStatus.OK.value()).message("Success confirm payment").success(true).data(payment).build();
        return ResponseEntity.ok(response);
    }
}
