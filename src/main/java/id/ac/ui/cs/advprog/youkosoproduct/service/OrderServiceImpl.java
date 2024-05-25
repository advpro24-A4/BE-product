package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{
    private final IOrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(IOrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order getOrder(long id, String userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found");
        }
        return order;
    }

    @Override
    public Order cancelOrder(long id, String userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found");
        }
        order.setStatus("CANCELLED");
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order finishOrder(long id, String userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found");
        }

        if (order.getStatus().equals("FINISHED")) {
            throw new BadRequestException("Order already finished");
        }

        if(order.getStatus().equals("CANCELLED")){
            throw new BadRequestException("Order already cancelled");
        }

        if(!order.getStatus().equals("DELIVERED")){
            throw new BadRequestException("Order not delivered yet");
        }

        order.setStatus("FINISHED");
        orderRepository.save(order);
        return order;
    }
}
