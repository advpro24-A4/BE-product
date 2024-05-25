package id.ac.ui.cs.advprog.youkosoproduct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long order_id;
    private String payment_method;
}
