package co.clean_architecture.api.refund.response;

import co.clean_architecture.model.refund.Refund;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class RefundResponse {

    private final Long id;
    private final Long paymentId;
    private final BigDecimal amount;
    private final String reason;
    private final LocalDateTime registeredAt;
    private final LocalDate createdAt;

    private RefundResponse(Long id, Long paymentId, BigDecimal amount, String reason, LocalDateTime registeredAt, LocalDate createdAt) {
        this.id = id;
        this.paymentId = paymentId;
        this.amount = amount;
        this.reason = reason;
        this.registeredAt = registeredAt;
        this.createdAt = createdAt;
    }

    public static RefundResponse fromDomain(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .paymentId(refund.getPaymentId())
                .amount(refund.getAmount())
                .reason(refund.getReason())
                .registeredAt(refund.getRegisteredAt())
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
