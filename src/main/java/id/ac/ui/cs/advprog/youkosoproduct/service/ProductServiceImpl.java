package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {
        return null;
    }

    @Override
    public List<Product> findAllProduct() {
        return null;
    }

    @Override
    public Product findProductById(String productId) {
        return null;
    }

    @Override
    public Product deleteProduct(String productId) {
        return null;
    }
}