package id.ac.ui.cs.advprog.youkosoproduct.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    private String ProductId;
    private String ProductName;
    private int ProductPrice;
    private int ProductStock;
    private int ProductDiscount;
    private String ProductDescription;
}