package co.clean_architecture.r2dbc.repository;

import co.clean_architecture.r2dbc.entity.UserAccountEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserAccountR2dbcRepository extends ReactiveCrudRepository<UserAccountEntity, Long> {

    Mono<UserAccountEntity> findByCustomerId(Long customerId);

    Mono<Boolean> existsByCustomerId(Long customerId);

    @Modifying
    @Query("""
        UPDATE user_account
        SET balance = :balance
        WHERE id = :id
    """)
    Mono<Integer> updateBalance(Long id, BigDecimal balance);

    @Modifying
    @Query("""
        UPDATE user_account
        SET status = :status
        WHERE id = :id
    """)
    Mono<Integer> updateStatus(Long id, String status);
}
