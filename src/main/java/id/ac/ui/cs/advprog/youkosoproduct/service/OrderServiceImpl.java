package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.dto.NotificationDTO;
import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.enumaration.NotificationType;
import id.ac.ui.cs.advprog.youkosoproduct.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{
    private final IOrderRepository orderRepository;
    private final KafkaService kafkaService;

    @Autowired
    public OrderServiceImpl(IOrderRepository orderRepository, KafkaService kafkaService) {

        this.kafkaService = kafkaService;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order getOrder(long id, String userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found from get"));
        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found from get order");
        }
        return order;
    }

    @Override
    public Order cancelOrder(long id, String userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found from cancel"));
        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found from cancel order");
        }
        order.setStatus("CANCELLED");
        orderRepository.save(order);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setOrderId(String.valueOf(order.getId()));
        notificationDTO.setUserId(order.getUserId());
        notificationDTO.setType(NotificationType.ORDER);
        notificationDTO.setMessage("Order with ID " + order.getId() + " has been cancelled");
        kafkaService.sendNotification(notificationDTO);

        return order;
    }

    @Override
    public Order finishOrder(long id, String userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found from finish"));
        if (!order.getUserId().equals(userId)) {
            throw new NotFoundException("Order not found from finish order");
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
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setOrderId(String.valueOf(order.getId()));
        notificationDTO.setUserId(order.getUserId());
        notificationDTO.setType(NotificationType.ORDER);
        notificationDTO.setMessage("Order with ID " + order.getId() + " has been finished");
        kafkaService.sendNotification(notificationDTO);
        orderRepository.save(order);
        return order;
    }
}
