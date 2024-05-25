package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Order;

import java.util.List;

public interface ICartItemService {
    List<CartItem> findByUserId(String userId);
    CartItem addProductToCartItem(String userId, int productId, int quantity);
    CartItem removeProductFromCartItem(String userId, int productId);
    Order checkout(String userId, String address, String recipientName, String recipientPhone);
}
