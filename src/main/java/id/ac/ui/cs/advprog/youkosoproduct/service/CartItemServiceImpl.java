package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.exception.BadRequestException;
import id.ac.ui.cs.advprog.youkosoproduct.exception.NotFoundException;
import id.ac.ui.cs.advprog.youkosoproduct.model.*;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.CartItemBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CartItemServiceImpl implements ICartItemService {
    private final ICartItemRepository cartItemRepository;
    private final ICartRepository cartRepository;
    private final IProductRepository productRepository;
    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;

    @Autowired
    public CartItemServiceImpl(ICartItemRepository cartItemRepository, ICartRepository cartRepository, IProductRepository productRepository, IOrderRepository orderRepository, IOrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public CartItem addProductToCartItem(String userId, int productId, int quantity) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId).orElse(null);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        if (product.getProductStock() <= 0) {
            throw new IllegalArgumentException("Product out of stock");
        }

        if (quantity > product.getProductStock()) {
            throw new IllegalArgumentException("Quantity exceeds stock");
        }

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });

        if (cartItem == null) {
            CartItemBuilder cartItemBuilder = new CartItemBuilder();
            cartItemBuilder.cart(cart);
            cartItemBuilder.product(product);
            cartItemBuilder.user(userId);
            cartItemBuilder.quantity(quantity);
            cartItemBuilder.price(product.finalPrice() * quantity);
            cartItem = cartItemBuilder.build();
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(cartItem.getPrice() + (product.finalPrice() * quantity));
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findByUserId(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public CartItem removeProductFromCartItem(String userId, int productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId).orElseThrow(() -> new NotFoundException("Cart item not found"));
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getPrice() - cartItem.getProduct().finalPrice());
        } else {
            cartItemRepository.delete(cartItem);
            return null;
        }
        return cartItem;
    }

    @Override
    @Transactional
    public Order checkout(String userId, String address, String recipientName, String recipientPhone) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setAlamatPengiriman(address);
        order.setNamaPenerima(recipientName);
        order.setNoHpPenerima(recipientPhone);

        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            if (product.getProductStock() < cartItem.getQuantity()) {
                throw new BadRequestException("Product out of stock");
            }

            product.setProductStock(product.getProductStock() - cartItem.getQuantity());
            productRepository.save(product);

            //TODO: send notification

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);
        }


        order.setOrderItems(orderItems);
        order = orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteByUserId(userId);
        cartItemRepository.deleteAll(cartItems);

        return order;
    }
}
