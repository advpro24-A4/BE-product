package id.ac.ui.cs.advprog.youkosoproduct.model.Builder;

import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.model.Customer;

public class CartItemBuilder {
    private int id;
    private Product product;
    private Customer customer;
    private int quantity;

    public CartItemBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CartItemBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public CartItemBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public CartItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartItem build() {
        CartItem cartItem = new CartItem();
        cartItem.setId(this.id);
        cartItem.setProduct(this.product);
        cartItem.setCustomer(this.customer);
        cartItem.setQuantity(this.quantity);
        return cartItem;
    }
}
