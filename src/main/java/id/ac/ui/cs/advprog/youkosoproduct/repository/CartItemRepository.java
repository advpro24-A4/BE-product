package id.ac.ui.cs.advprog.youkosoproduct.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.product.productId = ?2")
    void updateQuantity(int quantity, int productId);

    @Modifying
    @Query("DELETE from CartItem c WHERE c.product.productId = ?1")
    void deleteProduct(int productId);
}
