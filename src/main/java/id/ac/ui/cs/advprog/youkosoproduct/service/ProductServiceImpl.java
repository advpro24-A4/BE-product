package id.ac.ui.cs.advprog.youkosoproduct.service;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
