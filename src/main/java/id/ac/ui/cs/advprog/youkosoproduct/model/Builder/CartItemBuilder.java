package id.ac.ui.cs.advprog.youkosoproduct.model.Builder;

import id.ac.ui.cs.advprog.youkosoproduct.model.Cart;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;

public class CartItemBuilder {
    private int id;
    private Cart cart;
    private Product product;
    private String userId;
    private int quantity;
    private double price;

    public CartItemBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CartItemBuilder cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public CartItemBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public CartItemBuilder user(String userId) {
        this.userId = userId;
        return this;
    }

    public CartItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartItemBuilder price(double price) {
        this.price = price;
        return this;
    }

    public CartItem build() {
        CartItem cartItem = new CartItem();
        cartItem.setId(this.id);
        cartItem.setCart(this.cart);
        cartItem.setProduct(this.product);
        cartItem.setUserId(this.userId); // Ensure this method exists in CartItem
        cartItem.setQuantity(this.quantity);
        cartItem.setPrice(this.price);
        return cartItem;
    }
}