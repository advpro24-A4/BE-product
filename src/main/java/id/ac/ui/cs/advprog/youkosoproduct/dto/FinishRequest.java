package id.ac.ui.cs.advprog.youkosoproduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinishRequest {

    @JsonProperty("order_id")
    private Long orderId;
}
