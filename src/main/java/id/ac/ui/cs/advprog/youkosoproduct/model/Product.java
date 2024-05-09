package id.ac.ui.cs.advprog.youkosoproduct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(unique = true, length = 256, nullable = false, name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_stock")
    private int productStock;

    @Column(name = "product_discount")
    private int productDiscount;

    @Column(length = 512, nullable = false, name = "product_description")
    private String productDescription;
}