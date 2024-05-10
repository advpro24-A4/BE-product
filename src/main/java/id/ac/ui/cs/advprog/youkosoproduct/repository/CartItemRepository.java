package id.ac.ui.cs.advprog.youkosoproduct.repository;

import org.springframework.data.repository.CrudRepository;
import id.ac.ui.cs.advprog.youkosoproduct.model.CartItem;
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
}
