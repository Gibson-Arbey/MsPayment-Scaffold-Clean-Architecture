package co.clean_architecture.model.useraccount.gateways;

import co.clean_architecture.model.useraccount.UserAccount;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserAccountRepository {

    Mono<UserAccount> createUserAccount(UserAccount userAccount);

    Mono<UserAccount> getUserAccountByCustomerId(Long customerId);

    Mono<Void> updateUserAccountBalance(Long userAccountId, BigDecimal balance);

    Mono<Void> updateUserAccountStatus(Long userAccountId, String status);

    Mono<Boolean> existsByCustomerId(Long customerId);

    Mono<BigDecimal> getBalanceByUserAccountId(Long id);

    Mono<UserAccount> getUserAccountById(Long id);
}
