package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.repository.CartItemRepository;
import id.ac.ui.cs.advprog.youkosoproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public int addProduct(int productId, int quantity, Customer customer) {
        int updatedQuantity = quantity;
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

    public List<CartItem> listCartItems(Customer customer) {
        return cartItemRepository.findByCustomer(customer);
    }

    public int updateQuantity(int productId, int quantity, Customer customer) {
        cartItemRepository.updateQuantity(quantity, customer.getId(), productId);

        return productRepository.findById(productId)
                .map(product -> product.getProductPrice() * quantity)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + productId));
    }

    

}