package co.clean_architecture.model.payment;

import co.clean_architecture.model.payment.exception.InvalidPaymentException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {

    private static final int SCALE = 2;

    private Long id;
    private final Long userAccountId;
    private BigDecimal amount;
    private final String currency;
    private String status;
    private final LocalDateTime registeredAt;
    private final LocalDate createdAt;

    private Payment(
            Long userAccountId,
            BigDecimal amount,
            CurrencyEnum currency,
            LocalDateTime registeredAt,
            LocalDate createdAt
    ) {

        if (userAccountId == null) {
            throw new InvalidPaymentException("userAccountId is required");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentException("amount must be greater than zero");
        }

        this.userAccountId = userAccountId;
        this.amount = normalize(amount);
        this.currency = currency != null ? currency.name() : CurrencyEnum.COP.name();
        this.status = StatusPaymentEnum.PENDING.name();
        this.registeredAt = registeredAt != null ? registeredAt : LocalDateTime.now();
        this.createdAt = createdAt != null ? createdAt : LocalDate.now();
    }

    /* ================= FACTORIES ================= */

    public static Payment create(
            Long userAccountId,
            BigDecimal amount,
            CurrencyEnum currency
    ) {
        return new Payment(
                userAccountId,
                amount,
                currency,
                LocalDateTime.now(),
                LocalDate.now()
        );
    }

    public static Payment restore(
            Long id,
            Long userAccountId,
            BigDecimal amount,
            CurrencyEnum currency,
            StatusPaymentEnum status,
            LocalDateTime registeredAt,
            LocalDate createdAt
    ) {
        Payment payment = new Payment(
                userAccountId,
                amount,
                currency,
                registeredAt,
                createdAt
        );
        payment.id = id;
        payment.status = status.name();
        return payment;
    }

    /* ================= BEHAVIOR ================= */

    public void markAsCompleted() {
        if (!Objects.equals(this.status, StatusPaymentEnum.PENDING.name())) {
            throw new InvalidPaymentException("Only pending payments can be completed");
        }
        this.status = StatusPaymentEnum.COMPLETED.name();
    }

    public void markAsFailed() {
        this.status = StatusPaymentEnum.FAILED.name();
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

    /* ================= GETTERS ================= */

    public Long getId() {
        return id;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
