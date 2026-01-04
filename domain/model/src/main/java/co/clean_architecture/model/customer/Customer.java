package co.clean_architecture.model.customer;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Customer {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phone;

    private String status;

    private LocalDate createdAt;
}
