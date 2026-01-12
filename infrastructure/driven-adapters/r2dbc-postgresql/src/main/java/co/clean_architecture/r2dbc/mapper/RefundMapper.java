package co.clean_architecture.r2dbc.mapper;

import co.clean_architecture.model.refund.Refund;
import co.clean_architecture.r2dbc.entity.RefundEntity;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {

    public RefundEntity toEntity(Refund refund) {
        return RefundEntity.builder()
                .id(refund.getId())
                .paymentId(refund.getPaymentId())
                .amount(refund.getAmount())
                .reason(refund.getReason())
                .registeredAt(refund.getRegisteredAt())
                .createdAt(refund.getCreatedAt())
                .build();
    }

    public Refund toDomain(RefundEntity refundEntity) {
        return Refund.restore(
                refundEntity.getId(),
                refundEntity.getPaymentId(),
                refundEntity.getAmount(),
                refundEntity.getReason(),
                refundEntity.getRegisteredAt(),
                refundEntity.getCreatedAt()
        );
    }
}
