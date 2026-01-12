package co.clean_architecture.api.refund;

import co.clean_architecture.api.refund.request.CreateRefundRequest;
import co.clean_architecture.api.refund.response.RefundResponse;
import co.clean_architecture.model.refund.Refund;
import co.clean_architecture.usecase.refund.RefundUseCase;
import co.clean_architecture.usecase.refund.command.CreateRefundCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RefundHandler {

    private final RefundUseCase refundUseCase;

    public Mono<ServerResponse> createRefund(ServerRequest request) {
        return request.bodyToMono(CreateRefundRequest.class)
                .map(req -> new CreateRefundCommand(
                        req.getPaymentId(),
                        req.getAmount(),
                        req.getReason()
                ))
                .flatMap(refundUseCase::createRefund)
                .flatMap(refund ->
                        toResponse(refund, HttpStatus.CREATED)
                );
    }

    public Mono<ServerResponse> getRefundsByPaymentId(ServerRequest request) {
        Long paymentId = Long.parseLong(request.pathVariable("paymentId"));
        return ServerResponse.ok()
                .body(
                        refundUseCase.getRefundsByPaymentId(paymentId)
                                .map(RefundResponse::fromDomain),
                        RefundResponse.class
                );
    }


    private Mono<ServerResponse> toResponse(Refund refund, HttpStatus status) {
        return ServerResponse
                .status(status)
                .bodyValue(RefundResponse.fromDomain(refund));
    }
}
