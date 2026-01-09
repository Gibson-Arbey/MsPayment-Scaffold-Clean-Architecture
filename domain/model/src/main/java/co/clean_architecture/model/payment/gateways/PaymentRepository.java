package co.clean_architecture.model.payment.gateways;

import co.clean_architecture.model.payment.Payment;
import co.clean_architecture.model.payment.criteria.PaymentCriteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRepository {

    Mono<Payment> save(Payment payment);

    Mono<Payment> getById(Long id);

    Mono<Void> updateStatus(Long id, String status);

    Flux<Payment> getAllByFilters(PaymentCriteria criteria);
}
