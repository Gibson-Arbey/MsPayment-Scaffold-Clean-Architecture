package co.clean_architecture.r2dbc.adapter;

import co.clean_architecture.model.refund.Refund;
import co.clean_architecture.model.refund.gateways.RefundRepository;
import co.clean_architecture.r2dbc.mapper.RefundMapper;
import co.clean_architecture.r2dbc.repository.RefundR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RefundAdapter implements RefundRepository {

    private final RefundR2dbcRepository refundR2dbcRepository;
    private final RefundMapper refundMapper;

    @Override
    @Transactional
    public Mono<Refund> addRefund(Refund refund) {
        return refundR2dbcRepository
                .save(refundMapper.toEntity(refund))
                .map(refundMapper::toDomain);
    }

    @Override
    public Flux<Refund> findRefundsByPaymentId(Long paymentId) {
        return refundR2dbcRepository
                .findByPaymentId(paymentId)
                .map(refundMapper::toDomain);
    }
}
