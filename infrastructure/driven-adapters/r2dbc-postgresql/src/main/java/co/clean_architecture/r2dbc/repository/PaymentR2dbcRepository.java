package co.clean_architecture.r2dbc.repository;

import co.clean_architecture.r2dbc.entity.PaymentEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PaymentR2dbcRepository extends ReactiveCrudRepository<PaymentEntity, Long> {

    @Query("UPDATE payments SET paym_status = :status WHERE paym_id = :id")
    Mono<Void> updateStatusById(@Param("id") Long id, @Param("status") String status);
}
