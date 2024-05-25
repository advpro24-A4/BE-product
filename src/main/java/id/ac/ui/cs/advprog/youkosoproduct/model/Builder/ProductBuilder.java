package id.ac.ui.cs.advprog.youkosoproduct.model.Builder;

import id.ac.ui.cs.advprog.youkosoproduct.model.Product;

public class ProductBuilder {
    private int productId;
    private String productName;
    private int productPrice;
    private int productStock = 0;
    private int productDiscount = 0;
    private String productDescription;

    private String productImage;

    public ProductBuilder productId(int productId) {
        this.productId = productId;
        return this;
    }

    public ProductBuilder productName(String productName) {
        this.productName = productName;
        return this;
    }

    public ProductBuilder productPrice(int productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public ProductBuilder productStock(int productStock) {
        this.productStock = productStock;
        return this;
    }

    public ProductBuilder productDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
        return this;
    }

    public ProductBuilder productDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public ProductBuilder productImage(String productImage) {
        this.productImage = productImage;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(this.productId);
        product.setProductName(this.productName);
        product.setProductPrice(this.productPrice);
        product.setProductStock(this.productStock);
        product.setProductDiscount(this.productDiscount);
        product.setProductDescription(this.productDescription);
        product.setProductImage(this.productImage);
        return product;
    }


}


