package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;

public interface IPaymentService {
    Order payment(Long orderId, String paymentMethod, String userId);
    void editPayment(Long orderId, String userId, int newTotalPrice);
    Payment getPayment(Long orderId, String userId);
    Payment cancelPayment(Long orderId, String userId);
    Payment verifyPayment(Long orderId, String userId);
}