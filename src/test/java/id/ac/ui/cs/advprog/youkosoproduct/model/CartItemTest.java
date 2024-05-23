package id.ac.ui.cs.advprog.youkosoproduct.model;

import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.CartItemBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.ProductBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartItemTest {
    CartItem cartItem;
    Product product;
    Customer customer;

    @BeforeEach
    void SetUp() {
        product = new ProductBuilder()
                .productId(12345)
                .productName("Marvel Bishoujo Figure 1/7 Hawkeye / Kate Bishop")
                .productPrice(2249000)
                .productStock(5)
                .productDiscount(20)
                .productDescription("Am I the next Hawkeye!? The young bow-master, Kate Bishop, makes her debut.")
                .build();

        customer = new Customer();
        customer.setId(1);

        this.cartItem = new CartItemBuilder()
                .id(1)
                .product(product)
                .customer(customer)
                .quantity(2)
                .build();
    }

    @Test
    void testGetId() {
        assertEquals(1, this.cartItem.getId());
    }

    @Test
    void testGetProduct() {
        assertEquals(product, this.cartItem.getProduct());
    }

    @Test
    void testGetCustomer() {
        assertEquals(customer, this.cartItem.getCustomer());
    }

    @Test
    void testGetQuantity() {
        assertEquals(2, this.cartItem.getQuantity());
    }
}
