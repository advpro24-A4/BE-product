package id.ac.ui.cs.advprog.youkosoproduct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    private String recipient_address;
    private String recipient_name;
    private String recipient_phone_number;
}
