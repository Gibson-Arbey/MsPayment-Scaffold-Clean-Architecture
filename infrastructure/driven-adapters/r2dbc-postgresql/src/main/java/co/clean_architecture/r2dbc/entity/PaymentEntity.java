package co.clean_architecture.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @Column("paym_id")
    private Long id;

    @Column("usac_id")
    private Long userAccountId;

    @Column("paym_amount")
    private BigDecimal amount;

    @Column("paym_currency")
    private String currency;

    @Column("paym_status")
    private String status;

    @Column("paym_registeredat")
    private LocalDateTime registeredAt;

    @Column("paym_createdat")
    private LocalDate createdAt;
}
