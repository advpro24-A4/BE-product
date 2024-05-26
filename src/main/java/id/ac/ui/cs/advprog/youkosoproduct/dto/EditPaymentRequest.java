package id.ac.ui.cs.advprog.youkosoproduct.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditPaymentRequest {
    private Long order_id;
    private int new_price;
}
