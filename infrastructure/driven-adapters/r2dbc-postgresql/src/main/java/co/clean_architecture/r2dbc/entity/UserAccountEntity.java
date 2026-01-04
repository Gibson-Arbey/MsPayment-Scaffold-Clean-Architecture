package co.clean_architecture.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("useraccounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEntity {

    @Id
    @Column("usac_id")
    private Long id;

    @Column("cust_id")
    private Long customerId;

    @Column("usac_balance")
    private BigDecimal balance;

    @Column("usac_status")
    private String status;

    @Column("usac_createdat")
    private LocalDate createdAt;
}
