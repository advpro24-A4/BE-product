package id.ac.ui.cs.advprog.youkosoproduct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    private String recipientAddress;
    private String recipientName;
    private String recipientPhoneNumber;
}
