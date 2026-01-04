package co.clean_architecture.api.useraccount.response;

import co.clean_architecture.model.useraccount.UserAccount;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class UserAccountResponse {

    private Long id;
    private Long customerId;
    private BigDecimal balance;
    private String status;
    private LocalDate createdAt;

    public static UserAccountResponse fromDomain(UserAccount account) {
        return UserAccountResponse.builder()
            .id(account.getId())
            .customerId(account.getCustomerId())
            .balance(account.getBalance())
            .status(account.getStatus())
            .createdAt(account.getCreatedAt())
            .build();
    }
}
