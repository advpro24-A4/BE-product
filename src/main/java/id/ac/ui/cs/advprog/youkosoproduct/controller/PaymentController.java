package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.EditPaymentRequest;
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

        if (paymentRequest.getOrder_id() == null || paymentRequest.getPayment_method() == null) {
            throw new BadRequestException("Invalid request");
        }

        Order order = paymentService.payment(paymentRequest.getOrder_id(), paymentRequest.getPayment_method(), authResponse.getUser().getId());
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

        Payment payment = paymentService.cancelPayment(verifyDeletePaymentRequest.getOrder_id(), authResponse.getUser().getId());
        DefaultResponse<Payment> response = new DefaultResponseBuilder<Payment>().statusCode(HttpStatus.OK.value()).message("Success cancel payment").success(true).data(payment).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit-price/{orderId}")
    public ResponseEntity<DefaultResponse<String>> cancelPayment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("orderId") Long orderId,
            @RequestBody EditPaymentRequest editPaymentRequest
    ) {
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        paymentService.editPayment(orderId, authResponse.getUser().getId(), editPaymentRequest.getNew_price());
        DefaultResponse<String> response = new DefaultResponseBuilder<String>().statusCode(HttpStatus.OK.value()).message("Success edit payment").success(true).data(null).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-payment/{orderId}")
    public ResponseEntity<DefaultResponse<Payment>> cancelPayment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("orderId") Long orderId
    ) {
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Payment payment = paymentService.getPayment(orderId, authResponse.getUser().getId());
        DefaultResponse<Payment> response = new DefaultResponseBuilder<Payment>().statusCode(HttpStatus.OK.value()).message("Success get payment").success(true).data(payment).build();
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

        Payment payment = paymentService.verifyPayment(verifyDeletePaymentRequest.getOrder_id(), authResponse.getUser().getId());
        DefaultResponse<Payment> response = new DefaultResponseBuilder<Payment>().statusCode(HttpStatus.OK.value()).message("Success confirm payment").success(true).data(payment).build();
        return ResponseEntity.ok(response);
    }
}
