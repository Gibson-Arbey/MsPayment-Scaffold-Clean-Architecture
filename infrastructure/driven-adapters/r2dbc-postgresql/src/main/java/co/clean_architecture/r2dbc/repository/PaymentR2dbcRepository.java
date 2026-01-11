package co.clean_architecture.r2dbc.repository;

import co.clean_architecture.model.payment.criteria.PaymentCriteria;
import co.clean_architecture.r2dbc.entity.PaymentEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentR2dbcRepository extends ReactiveCrudRepository<PaymentEntity, Long> {

    @Query("UPDATE payments SET paym_status = :status WHERE paym_id = :id")
    Mono<Void> updateStatusById(@Param("id") Long id, @Param("status") String status);

    @Query("""
        SELECT *
        FROM payments
        WHERE
            (:userAccountId = 0 OR usac_id = :userAccountId)
        AND paym_amount BETWEEN :minAmount AND :maxAmount
        AND (:currency = '' OR paym_currency = :currency)
        AND (:status = '' OR paym_status = :status)
        AND paym_registeredat BETWEEN :registeredFrom AND :registeredTo
        ORDER BY paym_registeredat DESC
        LIMIT :limit OFFSET :offset
    """)
    Flux<PaymentEntity> findAllByFilters(
            @Param("userAccountId") Long userAccountId,
            @Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount,
            @Param("currency") String currency,
            @Param("status") String status,
            @Param("registeredFrom") LocalDateTime registeredFrom,
            @Param("registeredTo") LocalDateTime registeredTo,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

}
