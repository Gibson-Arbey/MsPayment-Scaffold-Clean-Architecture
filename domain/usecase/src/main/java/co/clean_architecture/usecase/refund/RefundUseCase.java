package co.clean_architecture.usecase.refund;

import co.clean_architecture.model.payment.exception.PaymentNotCreatedException;
import co.clean_architecture.model.payment.gateways.PaymentRepository;
import co.clean_architecture.model.refund.Refund;
import co.clean_architecture.model.refund.gateways.RefundRepository;
import co.clean_architecture.usecase.refund.command.CreateRefundCommand;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RefundUseCase {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    public Mono<Refund> createRefund(CreateRefundCommand command) {
        Refund refund = Refund.create(
                command.paymentId(),
                command.amount(),
                command.reason()
        );
        return paymentRepository.getById(refund.getPaymentId())
                .switchIfEmpty(
                        Mono.error(new PaymentNotCreatedException("Payment not found for ID: " + refund.getId()))
                ).flatMap(payment -> refundRepository.addRefund(refund));
    }

    public Flux<Refund> getRefundsByPaymentId(Long paymentId) {
        return refundRepository.findRefundsByPaymentId(paymentId);
    }
}
