package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DeleteRequest;
import id.ac.ui.cs.advprog.youkosoproduct.dto.FinishRequest;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.DefaultResponseBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService orderService;
    private final AuthService authService;

    @Autowired
    public OrderController(IOrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping("")
    public ResponseEntity<DefaultResponse<List<Order>>> getOrders(
            @RequestHeader("Authorization") String authHeader) {

        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Order> orders = orderService.getOrders(authResponse.getUser().getId());
        DefaultResponse<List<Order>> response = new DefaultResponseBuilder<List<Order>>().statusCode(HttpStatus.OK.value())
                .message("Success").success(true).data(orders).statusCode(HttpStatus.OK.value()).message("Success get orders").success(true).data(orders).build();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("")
    public ResponseEntity<DefaultResponse<Order>> cancelOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody DeleteRequest deleteRequest) {

        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Order order = orderService.cancelOrder(deleteRequest.getOrder_id(), authResponse.getUser().getId());
        DefaultResponse<Order> response = new DefaultResponseBuilder<Order>().statusCode(HttpStatus.OK.value())
                .message("Success").success(true).data(order).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/finish")
    public ResponseEntity<DefaultResponse<Order>> createOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FinishRequest finishRequest) {

        AuthResponse authResponse = authService.validateToken(authHeader).join();
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Order order = orderService.finishOrder(finishRequest.getOrder_id(), authResponse.getUser().getId());

        DefaultResponse<Order> response = new DefaultResponseBuilder<Order>().statusCode(HttpStatus.OK.value())
                .message("Success").success(true).data(order).build();

        return ResponseEntity.ok(response);
    }
}