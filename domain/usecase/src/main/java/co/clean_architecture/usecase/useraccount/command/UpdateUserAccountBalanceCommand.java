package co.clean_architecture.usecase.useraccount.command;

import java.math.BigDecimal;

public record UpdateUserAccountBalanceCommand(
        Long userAccountId,
        BigDecimal balance
) {
}
