package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;
import id.ac.ui.cs.advprog.youkosoproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        productRepository.createProduct(product);
        return product;
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {
        productRepository.updateProduct(productId, updatedProduct);
        return updatedProduct;
    }

    @Override
    public List<Product> findAllProduct() {
        Iterator<Product> productIterator = productRepository.findAllProduct();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findProductById(String productId) {
        return productRepository.findProductById(productId);
    }

    @Override
    public Product deleteProduct(String productId) {
        return productRepository.deleteProduct(productId);
    }
}