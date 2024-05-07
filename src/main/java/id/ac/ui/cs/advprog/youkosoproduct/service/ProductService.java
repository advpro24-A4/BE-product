package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(String productId, Product updatedProduct);
    List <Product> findAllProduct();
    Product findProductById(String productId);
    Product deleteProduct(String productId);
}