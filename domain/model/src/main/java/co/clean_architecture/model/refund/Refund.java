package co.clean_architecture.model.refund;

import co.clean_architecture.model.payment.exception.InvalidPaymentException;
import co.clean_architecture.model.refund.exception.InvalidRefundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Refund {

    private static final int SCALE = 2;

    private Long id;
    private final Long paymentId;
    private BigDecimal amount;
    private String reason;
    private final LocalDateTime registeredAt;
    private final LocalDate createdAt;

    private Refund(Long paymentId, BigDecimal amount, String reason, LocalDateTime registeredAt, LocalDate createdAt) {

        if (paymentId == null) {
            throw new InvalidPaymentException("the payment is required");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentException("amount must be greater than zero");
        }

        if(reason == null || reason.isBlank()) {
            throw new InvalidRefundException("reason is required");
        }

        this.paymentId = paymentId;
        this.amount = normalize(amount);
        this.reason = reason;
        this.registeredAt = registeredAt;
        this.createdAt = createdAt;
    }

    /* ================= FACTORIES ================= */
    public static Refund create(Long paymentId, BigDecimal amount, String reason) {
        return new Refund(
                paymentId,
                amount,
                reason,
                LocalDateTime.now(),
                LocalDate.now()
        );
    }

    public static Refund restore(
            Long id,
            Long paymentId,
            BigDecimal amount,
            String reason,
            LocalDateTime registeredAt,
            LocalDate createdAt
    ) {
        Refund refund = new Refund(
                paymentId,
                amount,
                reason,
                registeredAt,
                createdAt
        );
        refund.id = id;
        return refund;
    }

    /* ================= HELPERS ================= */

    private static BigDecimal normalize(BigDecimal value) {
        if (value.scale() > SCALE) {
            throw new InvalidPaymentException(
                    "Amount cannot have more than 2 decimal places"
            );
        }
        return value.setScale(SCALE, RoundingMode.HALF_UP);
    }

    // ================= GETTERS ================= //

    public Long getId() {
        return id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

}
