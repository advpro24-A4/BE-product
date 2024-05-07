package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);
    public Product updateProduct(String productId, Product updatedProduct);
    public List <Product> findAllProduct();
    public Product findProductById(String productId);
    public Product deleteProduct(String productId);
}