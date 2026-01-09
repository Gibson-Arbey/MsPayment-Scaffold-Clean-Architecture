package co.clean_architecture.r2dbc.adapter;

import co.clean_architecture.model.payment.Payment;
import co.clean_architecture.model.payment.criteria.PaymentCriteria;
import co.clean_architecture.model.payment.gateways.PaymentRepository;
import co.clean_architecture.r2dbc.mapper.PaymentMapper;
import co.clean_architecture.r2dbc.repository.PaymentR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentRepository {

    private final PaymentMapper paymentMapper;
    private final PaymentR2dbcRepository paymentR2dbcRepository;

    @Override
    @Transactional
    public Mono<Payment> save(Payment payment) {
        return paymentR2dbcRepository.save(
                paymentMapper.toEntity(payment)
        ).map(paymentMapper::toDomain);
    }

    @Override
    public Mono<Payment> getById(Long id) {
        return paymentR2dbcRepository.findById(id).map(paymentMapper::toDomain);
    }

    @Override
    @Transactional
    public Mono<Void> updateStatus(Long id, String status) {
        return paymentR2dbcRepository.updateStatusById(id, status);
    }

    @Override
    public Flux<Payment> getAllByFilters(PaymentCriteria criteria) {
        return null;
    }
}
