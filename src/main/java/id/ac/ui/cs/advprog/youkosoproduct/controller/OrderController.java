package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {




    @GetMapping("")
    public ResponseEntity<DefaultResponse<List<Order>>> getOrders() {

        return null;
    }

}
