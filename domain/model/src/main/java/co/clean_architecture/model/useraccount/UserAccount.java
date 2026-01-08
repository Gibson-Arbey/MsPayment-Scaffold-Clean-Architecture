package co.clean_architecture.model.useraccount;

import co.clean_architecture.model.customer.exception.CustomerNotExistsException;
import co.clean_architecture.model.useraccount.exception.InvalidBalanceOperationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserAccount {

    private Long id;
    private final Long customerId;
    private final LocalDate createdAt;
    private BigDecimal balance;
    private String status;

    private UserAccount(Long customerId, BigDecimal balance, LocalDate createdAt) {
        if (customerId == null) {
            throw new CustomerNotExistsException("customerId is required");
        }
        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBalanceOperationException("balance must be >= 0");
        }

        if (balance.scale() > 2) {
            throw new InvalidBalanceOperationException(
                "Balance cannot have more than 2 decimal places"
            );
        }

        this.customerId = customerId;
        this.balance = balance;
        this.createdAt = createdAt;
        this.status = StatusUserAccountEnum.ACTIVE.name();
    }

    public static UserAccount create(Long customerId, BigDecimal balance, LocalDate createdAt) {
        return new UserAccount(customerId, balance, createdAt);
    }

    public static UserAccount restore(
            Long id,
            Long customerId,
            BigDecimal balance,
            StatusUserAccountEnum status,
            LocalDate createdAt
    ) {
        UserAccount account = new UserAccount(customerId, balance, createdAt);
        account.id = id;
        account.status = status.name();
        return account;
    }


    public void updateBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("balance must be >= 0");
        }
        this.balance = newBalance;
    }

    public void changeStatus(StatusUserAccountEnum newStatus) {
        this.status = newStatus.name();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

}
