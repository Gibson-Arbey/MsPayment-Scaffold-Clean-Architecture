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

    private PaymentCriteria(
            Long userAccountId,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String currency,
            String status,
            LocalDateTime registeredFrom,
            LocalDateTime registeredTo
    ) {
        this.userAccountId = userAccountId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.currency = currency;
        this.status = status;
        this.registeredFrom = registeredFrom;
        this.registeredTo = registeredTo;
    }

    public static PaymentCriteria of(
            Long userAccountId,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String currency,
            String status,
            LocalDateTime registeredFrom,
            LocalDateTime registeredTo
    ) {
        return new PaymentCriteria(
                userAccountId,
                minAmount,
                maxAmount,
                currency,
                status,
                registeredFrom,
                registeredTo
        );
    }
}
