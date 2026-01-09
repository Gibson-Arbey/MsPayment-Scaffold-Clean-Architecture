package co.clean_architecture.api.payment.response;

import co.clean_architecture.model.payment.Payment;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentResponse {
    private final Long id;
    private final Long userAccountId;
    private final BigDecimal amount;
    private final String currency;
    private final String status;
    private final LocalDateTime registeredAt;
    private final LocalDate createdAt;


    private PaymentResponse(Long id, Long userAccountId, BigDecimal amount, String currency, String status, LocalDateTime registeredAt, LocalDate createdAt) {
        this.id = id;
        this.userAccountId = userAccountId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.registeredAt = registeredAt;
        this.createdAt = createdAt;
    }

    public static PaymentResponse fromDomain(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .userAccountId(payment.getUserAccountId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .registeredAt(payment.getRegisteredAt())
                .createdAt(payment.getCreatedAt())
                .build();
    }

}
