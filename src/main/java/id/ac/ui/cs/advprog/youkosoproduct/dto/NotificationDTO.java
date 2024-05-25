package id.ac.ui.cs.advprog.youkosoproduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.ac.ui.cs.advprog.youkosoproduct.model.enumaration.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private String message;
    private NotificationType type;
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("shipment_id")
    private String shipmentId;
}
