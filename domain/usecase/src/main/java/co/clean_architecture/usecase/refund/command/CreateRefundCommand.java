package co.clean_architecture.usecase.refund.command;

import java.math.BigDecimal;

public record CreateRefundCommand(
        Long paymentId,
        BigDecimal amount,
        String reason
) {
}
