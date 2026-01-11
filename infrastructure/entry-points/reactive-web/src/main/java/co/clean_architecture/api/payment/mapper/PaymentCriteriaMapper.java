package co.clean_architecture.api.payment.mapper;

import co.clean_architecture.model.payment.criteria.PaymentCriteria;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentCriteriaMapper {

    private static final int MAX_LIMIT = 100;

    public PaymentCriteria fromRequest(ServerRequest request) {

        Integer limit = request.queryParam("limit")
                .map(Integer::parseInt)
                .map(l -> Math.min(l, MAX_LIMIT))
                .orElse(20);

        Integer offset = request.queryParam("offset")
                .map(Integer::parseInt)
                .orElse(0);
        return PaymentCriteria.of(
                request.queryParam("userAccountId").map(Long::valueOf).orElse(null),
                request.queryParam("minAmount").map(BigDecimal::new).orElse(null),
                request.queryParam("maxAmount").map(BigDecimal::new).orElse(null),
                request.queryParam("currency").orElse(null),
                request.queryParam("status").orElse(null),
                request.queryParam("registeredFrom").map(LocalDateTime::parse).orElse(null),
                request.queryParam("registeredTo").map(LocalDateTime::parse).orElse(null),
                limit,
                offset
        );
    }
}

