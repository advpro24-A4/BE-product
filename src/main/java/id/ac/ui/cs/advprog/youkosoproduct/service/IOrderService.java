package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getOrders(String userId);
    Order getOrder(long id, String userId);
    Order cancelOrder(long id, String userId);
    Order finishOrder(long id, String userId);
}
