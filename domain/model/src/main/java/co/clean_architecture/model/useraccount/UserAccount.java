package co.clean_architecture.model.useraccount;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserAccount {

    private Long id;
    private Long customerId;
    private BigDecimal balance;
    private String status;
    private LocalDate createdAt;
}
