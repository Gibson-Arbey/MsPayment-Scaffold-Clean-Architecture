package co.clean_architecture.r2dbc.repository;

import co.clean_architecture.r2dbc.entity.RefundEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RefundR2dbcRepository extends ReactiveCrudRepository<RefundEntity, Long> {

    @Query("""
        SELECT * 
        FROM refunds
        WHERE paym_id = :paymentId
    """)
    Flux<RefundEntity> findByPaymentId(@Param("paymentId") Long paymentId);
}
