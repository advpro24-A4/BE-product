package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Customer;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    List<CartItem> findByCustomer(Customer customer);

    CartItem findByCustomerAndProduct(Customer customer, Product product);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.product.productId = ?3")
    void updateQuantity(int quantity, int customerId, int productId);

    @Modifying
    @Query("DELETE from CartItem c WHERE c.customer.id = ?1 AND c.product.productId = ?2")
    void deleteByCustomerAndProduct(int customerId, int productId);
}
