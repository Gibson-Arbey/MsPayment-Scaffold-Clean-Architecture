package co.clean_architecture.api.payment.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePaymentRequest {

    private Long userAccountId;
    private BigDecimal amount;
    private String currency;

}
