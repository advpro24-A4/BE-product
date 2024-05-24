package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();
}
