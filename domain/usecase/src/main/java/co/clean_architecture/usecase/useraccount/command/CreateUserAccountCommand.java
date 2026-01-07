package co.clean_architecture.usecase.useraccount.command;

import java.math.BigDecimal;

public record CreateUserAccountCommand(
        Long customerId,
        BigDecimal balance) {
}
