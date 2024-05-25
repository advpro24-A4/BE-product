package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getOrders(String userId);
    Order getOrder(long id);
    Order deleteOrder(long id);
}
