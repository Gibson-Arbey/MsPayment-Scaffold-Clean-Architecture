package co.clean_architecture.model.payment.criteria;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentCriteria {

    private final Long userAccountId;

    private final BigDecimal minAmount;

    private final BigDecimal maxAmount;

    private final String currency;

    private final String status;

    private final LocalDateTime registeredFrom;

    private final LocalDateTime registeredTo;

    private final int limit;

    private final int offset;

    private PaymentCriteria(
            Long userAccountId,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String currency,
            String status,
            LocalDateTime registeredFrom,
            LocalDateTime registeredTo,
            int limit,
            int offset
    ) {
        this.userAccountId = userAccountId != null ? userAccountId : 0L;
        this.minAmount = minAmount != null ? minAmount : BigDecimal.ZERO;
        this.maxAmount = maxAmount != null ? maxAmount : BigDecimal.valueOf(Double.MAX_VALUE);
        this.currency = currency != null ? currency : "";
        this.status = status != null ? status : "";
        this.registeredFrom = registeredFrom != null
                ? registeredFrom
                : LocalDateTime.of(1970, 1, 1, 0, 0);
        this.registeredTo = registeredTo != null
                ? registeredTo
                : LocalDateTime.now();

        this.limit = limit;
        this.offset = offset;
    }

    public static PaymentCriteria of(
            Long userAccountId,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String currency,
            String status,
            LocalDateTime registeredFrom,
            LocalDateTime registeredTo,
            Integer limit,
            Integer offset
    ) {
        return new PaymentCriteria(
                userAccountId,
                minAmount,
                maxAmount,
                currency,
                status,
                registeredFrom,
                registeredTo,
                limit != null ? limit : 20,
                offset != null ? offset : 0
        );
    }
}
