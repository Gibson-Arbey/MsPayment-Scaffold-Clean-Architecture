package co.clean_architecture.api.payment;

import co.clean_architecture.api.payment.mapper.PaymentCriteriaMapper;
import co.clean_architecture.api.payment.request.CreatePaymentRequest;
import co.clean_architecture.api.payment.response.PaymentResponse;
import co.clean_architecture.api.useraccount.response.UserAccountResponse;
import co.clean_architecture.model.payment.Payment;
import co.clean_architecture.model.payment.criteria.PaymentCriteria;
import co.clean_architecture.model.useraccount.UserAccount;
import co.clean_architecture.usecase.payment.PaymentUseCase;
import co.clean_architecture.usecase.payment.command.CreatePaymentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentHandler {

    private final PaymentUseCase paymentUseCase;

    // mappers
    private final PaymentCriteriaMapper paymentCriteriaMapper;

    public Mono<ServerResponse> createPayment(ServerRequest request) {
        return request.bodyToMono(CreatePaymentRequest.class)
                .map(req -> new CreatePaymentCommand(
                        req.getUserAccountId(),
                        req.getAmount(),
                        req.getCurrency()
                ))
                .flatMap(paymentUseCase::createPayment)
                .flatMap(payment ->
                        toResponse(payment, HttpStatus.CREATED)
                );
    }

    public Mono<ServerResponse> getPaymentById(ServerRequest request) {
        Long paymentId = Long.parseLong(request.pathVariable("id"));
        return paymentUseCase.getPaymentById(paymentId)
                .flatMap(payment -> toResponse(payment, HttpStatus.OK));
    }

    public Mono<ServerResponse> updatePaymentStatus(ServerRequest request) {
        Long paymentId = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(String.class)
                .flatMap(status -> paymentUseCase.updatePaymentStatus(paymentId, status))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getAllPaymentsByFilters(ServerRequest request) {
        PaymentCriteria criteria = paymentCriteriaMapper.fromRequest(request);
        return ServerResponse.ok()
            .body(
                paymentUseCase.getAllPayments(criteria)
                    .map(PaymentResponse::fromDomain),
                    PaymentResponse.class
            );
    }

    private Mono<ServerResponse> toResponse(Payment payment, HttpStatus status) {
        return ServerResponse
                .status(status)
                .bodyValue(PaymentResponse.fromDomain(payment));
    }
}
