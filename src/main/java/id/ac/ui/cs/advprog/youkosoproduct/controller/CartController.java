package id.ac.ui.cs.advprog.youkosoproduct.controller;


import id.ac.ui.cs.advprog.youkosoproduct.dto.*;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.DefaultResponseBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final ICartItemService cartItemService;
    private final AuthService authService;

    @Autowired
    public CartController(ICartItemService cartItemService, AuthService authService) {
        this.cartItemService = cartItemService;
        this.authService = authService;
    }

    @GetMapping("")
    public ResponseEntity<DefaultResponse<List<CartItem>>> getCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ){
        AuthResponse authResponse = authService.validateToken(authHeader).join();

        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CartItem> cartItems = cartItemService.findByUserId(authResponse.getUser().getId());
        DefaultResponse<List<CartItem>> response = new DefaultResponseBuilder<List<CartItem>>()
                .statusCode(HttpStatus.OK.value())
                .message("Success find cart")
                .success(true)
                .data(cartItems)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<DefaultResponse<CartItem>> addProductToCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody AddCartRequest addCartRequest
    ){
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CartItem cartItem = cartItemService.addProductToCartItem(authResponse.getUser().getId(), addCartRequest.getProduct_id(), addCartRequest.getQuantity());
        DefaultResponse<CartItem> response = new DefaultResponseBuilder<CartItem>()
                .statusCode(HttpStatus.OK.value())
                .message("Success add product")
                .success(true)
                .data(cartItem)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<DefaultResponse<CartItem>> removeProductFromCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody RemoveCartRequest removeCartRequest
    ){
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CartItem cartItem = cartItemService.removeProductFromCartItem(authResponse.getUser().getId(), removeCartRequest.getProduct_id());
        DefaultResponse<CartItem> response = new DefaultResponseBuilder<CartItem>()
                .statusCode(HttpStatus.OK.value())
                .message("Success remove product")
                .success(true)
                .data(cartItem)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkout")
    public ResponseEntity<DefaultResponse<Order>> checkout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody CheckoutRequest checkoutRequest
    ){
        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Order order = cartItemService.checkout(authResponse.getUser().getId(), checkoutRequest.getRecipient_address(), checkoutRequest.getRecipient_name(), checkoutRequest.getRecipient_phone_number());
        DefaultResponse<Order> response = new DefaultResponseBuilder<Order>()
                .statusCode(HttpStatus.OK.value())
                .message("Success checkout")
                .success(true)
                .data(order)
                .build();
        return ResponseEntity.ok(response);
    }
}