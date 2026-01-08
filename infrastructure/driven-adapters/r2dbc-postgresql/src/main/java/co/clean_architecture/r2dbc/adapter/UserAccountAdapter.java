package co.clean_architecture.r2dbc.adapter;

import co.clean_architecture.model.useraccount.UserAccount;
import co.clean_architecture.model.useraccount.exception.UserAccountNotFoundException;
import co.clean_architecture.model.useraccount.gateways.UserAccountRepository;
import co.clean_architecture.r2dbc.mapper.UserAccountMapper;
import co.clean_architecture.r2dbc.repository.UserAccountR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class UserAccountAdapter implements UserAccountRepository {

    private final UserAccountR2dbcRepository userAccountR2dbcRepository;
    private final UserAccountMapper userAccountMapper;

    @Override
    @Transactional()
    public Mono<UserAccount> createUserAccount(UserAccount userAccount) {
        return userAccountR2dbcRepository
                .save(userAccountMapper.toEntity(userAccount))
                .map(userAccountMapper::toDomain);
    }

    @Override
    public Mono<UserAccount> getUserAccountByCustomerId(Long customerId) {
        return userAccountR2dbcRepository
                .findByCustomerId(customerId)
                .map(userAccountMapper::toDomain);
    }

    @Override
    @Transactional
    public Mono<Void> updateUserAccountBalance(Long userAccountId, BigDecimal balance) {
        return userAccountR2dbcRepository.updateBalance(userAccountId, balance)
            .flatMap(rows -> {
                if (rows == 0) {
                    return Mono.error(
                        new UserAccountNotFoundException(
                            "User account not found with id: " + userAccountId
                        )
                    );
                }
                return Mono.empty();
            });
    }

    @Override
    @Transactional
    public Mono<Void> updateUserAccountStatus(Long userAccountId, String status) {
        return userAccountR2dbcRepository.updateStatus(userAccountId, status)
            .flatMap(rows -> {
                if (rows == 0) {
                    return Mono.error(
                        new UserAccountNotFoundException(
                            "User account not found with id: " + userAccountId
                        )
                    );
                }
                return Mono.empty();
            });
    }

    @Override
    public Mono<Boolean> existsByCustomerId(Long customerId) {
        return userAccountR2dbcRepository.existsByCustomerId(customerId);
    }

    @Override
    public Mono<BigDecimal> getBalanceByUserAccountId(Long id) {
        return userAccountR2dbcRepository.findBalanceById(id);
    }
}
