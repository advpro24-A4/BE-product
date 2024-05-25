package id.ac.ui.cs.advprog.youkosoproduct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartRequest {
    private int product_id;
    private int quantity;

}
