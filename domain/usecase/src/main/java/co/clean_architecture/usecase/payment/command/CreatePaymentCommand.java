package co.clean_architecture.usecase.payment.command;

import java.math.BigDecimal;

public record CreatePaymentCommand(
        Long userAccountId,
        BigDecimal amount,
        String currency
) {
}
