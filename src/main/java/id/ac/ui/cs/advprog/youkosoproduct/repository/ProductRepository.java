package id.ac.ui.cs.advprog.youkosoproduct.repository;


import org.springframework.stereotype.Repository;
import id.ac.ui.cs.advprog.youkosoproduct.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product createProduct(Product product) {
        if (product.getProductId() == null) {
            UUID uuid = UUID.randomUUID();
            product.setProductId(uuid.toString());
        }

        productData.add(product);
        return product;
    }

    public Product updateProduct(String productId, Product updatedProduct) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                product.setProductName(updatedProduct.getProductName());
                product.setProductPrice(updatedProduct.getProductPrice());
                product.setProductStock(updatedProduct.getProductStock());
                product.setProductDiscount(updatedProduct.getProductDiscount());
                product.setProductDescription(updatedProduct.getProductDescription());
                return product;
            }
        }
        return null;
    }

    public Iterator<Product> findAllProduct() {
        return productData.iterator();
    }

    public Product findProductById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid product Id:" + productId)
                );
    }

    public Product deleteProduct(String productId) {
        Product product = findProductById(productId);
        productData.remove(product);
        return product;
    }

}
