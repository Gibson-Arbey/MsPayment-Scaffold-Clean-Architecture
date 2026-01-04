package co.clean_architecture.usecase.useraccount;

import co.clean_architecture.model.customer.Customer;
import co.clean_architecture.model.customer.exception.CustomerNotExistsException;
import co.clean_architecture.model.customer.gateways.CustomerRepository;
import co.clean_architecture.model.useraccount.UserAccount;
import co.clean_architecture.model.useraccount.exception.UserAccountAlreadyExistsException;
import co.clean_architecture.model.useraccount.gateways.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserAccountUseCase {

    private final UserAccountRepository userAccountRepository;
    private final CustomerRepository customerRepository;

    public Mono<UserAccount> createUserAccount(UserAccount userAccount) {
        Long customerId = userAccount.getCustomerId();
        userAccount.setCreatedAt(LocalDate.now());
        userAccount.setStatus(StatusUserAccountEnum.ACTIVE.name());
        return customerRepository.findById(customerId)
            .switchIfEmpty(Mono.error(
                new CustomerNotExistsException("Customer not found with ID: " + customerId)
            ))
            .flatMap(customer ->
                userAccountRepository.existsByCustomerId(customerId)
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(
                    new UserAccountAlreadyExistsException(
                        "User account already exists for customer ID: " + customerId
                    )
                ))
                .then(userAccountRepository.createUserAccount(userAccount))
            );
    }

}
