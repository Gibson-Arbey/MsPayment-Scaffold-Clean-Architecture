package co.clean_architecture.model.refund.gateways;

import co.clean_architecture.model.refund.Refund;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RefundRepository {

    Mono<Refund> addRefund(Refund refund);

    Flux<Refund> findRefundsByPaymentId(Long paymentId);
}
