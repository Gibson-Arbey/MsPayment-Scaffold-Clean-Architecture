package co.clean_architecture.api.refund.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CreateRefundRequest {

    private Long paymentId;
    private BigDecimal amount;
    private String reason;
}
