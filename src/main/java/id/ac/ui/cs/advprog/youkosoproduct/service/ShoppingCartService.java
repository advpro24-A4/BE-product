package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Customer;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public Integer addProduct(Integer productId, Integer quantity, Customer customer) {
        Integer updatedQuantity = quantity;
        Product product = new Product(productId);

        CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);

        if (cartItem != null) {
            updatedQuantity = cartItem.getQuantity() + quantity;
        } else {
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(updatedQuantity);
        cartItemRepository.save(cartItem);

        return updatedQuantity;
    }
}
