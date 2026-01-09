package co.clean_architecture.r2dbc.mapper;

import co.clean_architecture.model.payment.CurrencyEnum;
import co.clean_architecture.model.payment.Payment;
import co.clean_architecture.model.payment.StatusPaymentEnum;
import co.clean_architecture.r2dbc.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .userAccountId(payment.getUserAccountId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .registeredAt(payment.getRegisteredAt())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment toDomain(PaymentEntity paymentEntity) {
        return Payment.restore(
                paymentEntity.getId(),
                paymentEntity.getUserAccountId(),
                paymentEntity.getAmount(),
                CurrencyEnum.valueOf(paymentEntity.getCurrency()),
                StatusPaymentEnum.valueOf(paymentEntity.getStatus()),
                paymentEntity.getRegisteredAt(),
                paymentEntity.getCreatedAt()
        );
    }
}
