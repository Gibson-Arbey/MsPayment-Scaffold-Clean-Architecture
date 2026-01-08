package co.clean_architecture.r2dbc.repository;

import co.clean_architecture.r2dbc.entity.UserAccountEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserAccountR2dbcRepository
        extends ReactiveCrudRepository<UserAccountEntity, Long> {

    @Query("""
        SELECT *
        FROM useraccounts
        WHERE cust_id = :customerId
    """)
    Mono<UserAccountEntity> findByCustomerId(Long customerId);

    Mono<Boolean> existsByCustomerId(Long customerId);

    @Query("""
        SELECT usac_balance
        FROM useraccounts
        WHERE usac_id = :id
    """)
    Mono<BigDecimal> findBalanceById(Long id);

    @Modifying
    @Query("""
    UPDATE useraccounts
    SET usac_balance = :balance
    WHERE usac_id = :id
""")
    Mono<Integer> updateBalance(
            @Param("id") Long id,
            @Param("balance") BigDecimal balance
    );


    @Modifying
    @Query("""
        UPDATE useraccounts
        SET usac_status = :status
        WHERE usac_id = :id
    """)
    Mono<Integer> updateStatus(
            @Param("id") Long id,
            @Param("status") String status);
}
