package co.clean_architecture.usecase.useraccount;

import co.clean_architecture.model.customer.exception.CustomerNotExistsException;
import co.clean_architecture.model.customer.gateways.CustomerRepository;
import co.clean_architecture.model.useraccount.UserAccount;
import co.clean_architecture.model.useraccount.exception.InvalidBalanceOperationException;
import co.clean_architecture.model.useraccount.exception.UserAccountAlreadyExistsException;
import co.clean_architecture.model.useraccount.exception.UserAccountNotFoundException;
import co.clean_architecture.model.useraccount.gateways.UserAccountRepository;
import co.clean_architecture.usecase.useraccount.command.CreateUserAccountCommand;
import co.clean_architecture.usecase.useraccount.command.UpdateUserAccounStatusCommand;
import co.clean_architecture.usecase.useraccount.command.UpdateUserAccountBalanceCommand;
import co.clean_architecture.usecase.useraccount.exception.StatusUserAccountNotValidException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UserAccountUseCase {

    private final UserAccountRepository userAccountRepository;
    private final CustomerRepository customerRepository;

    public Mono<UserAccount> createUserAccount(CreateUserAccountCommand command) {
        UserAccount userAccount = UserAccount.create(
                command.customerId(),
                command.balance(),
                LocalDate.now()
        );
        Long customerId = command.customerId();
        return customerRepository.findById(customerId)
            .flatMap(customer ->
                userAccountRepository.existsByCustomerId(customerId)
                .filter(exists -> !exists)
                    .switchIfEmpty(Mono.error(
                        new UserAccountAlreadyExistsException(
                        "User account already exists for customer ID: " + customerId
                        )
                    )).then(userAccountRepository.createUserAccount(userAccount))
            );
    }

    public Mono<UserAccount> getUserAccountByCustomerId(Long customerId) {
        return userAccountRepository.getUserAccountByCustomerId(customerId)
                .switchIfEmpty(Mono.error(
                        new UserAccountNotFoundException(
                                "User account not found for customer ID: " + customerId
                        )
                ));
    }

    public Mono<Void> updateUserAccountBalance(UpdateUserAccountBalanceCommand command) {
        Long userAccountId = command.userAccountId();
        BigDecimal delta = command.balance();
        return userAccountRepository.getBalanceByUserAccountId(userAccountId)
            .switchIfEmpty(Mono.error(
                new UserAccountNotFoundException(
                    "User account not found for ID: " + userAccountId
                )
            ))
            .map(currentBalance -> calculateNewBalance(currentBalance, delta))
            .flatMap(newBalance ->
                userAccountRepository.updateUserAccountBalance(userAccountId, newBalance)
            );
    }

    public Mono<Void> updateUserAccountStatus(UpdateUserAccounStatusCommand command) {
        Long userAccountId = command.userAccountId();
        String status = command.status();
        validateStatus(status);
        return userAccountRepository.updateUserAccountStatus(userAccountId, status);
    }

    private void validateStatus(String status) {
        try {
            co.clean_architecture.model.useraccount.StatusUserAccountEnum.valueOf(status);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new StatusUserAccountNotValidException("Invalid status: " + status);
        }
    }

    private BigDecimal calculateNewBalance(BigDecimal current, BigDecimal delta) {
        BigDecimal newBalance = current.add(delta);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBalanceOperationException(
                    "Balance cannot be negative"
            );
        }
        if (newBalance.scale() > 2) {
            throw new InvalidBalanceOperationException(
                    "Balance cannot have more than 2 decimal places"
            );
        }
        return newBalance;
    }

}
