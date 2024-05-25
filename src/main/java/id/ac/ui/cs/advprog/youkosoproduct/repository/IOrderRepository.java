package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(String userId);
    Optional<Order> findById(long id);
}
