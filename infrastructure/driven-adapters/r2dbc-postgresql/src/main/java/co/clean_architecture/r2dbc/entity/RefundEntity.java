package co.clean_architecture.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("refunds")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundEntity {

    @Id
    @Column("refu_id")
    private Long id;

    @Column("paym_id")
    private Long paymentId;

    @Column("refu_reason")
    private String reason;

    @Column("refu_amount")
    private BigDecimal amount;

    @Column("refu_registeredat")
    private LocalDateTime registeredAt;

    @Column("refu_createdat")
    private LocalDate createdAt;
}
