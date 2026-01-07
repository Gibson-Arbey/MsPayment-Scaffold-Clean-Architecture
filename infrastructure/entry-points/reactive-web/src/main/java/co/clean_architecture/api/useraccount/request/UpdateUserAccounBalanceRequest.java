package co.clean_architecture.api.useraccount.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateUserAccounBalanceRequest {

    private BigDecimal balance;
}
