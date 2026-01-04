package co.clean_architecture.consumer.customer;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerResponse {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phone;

    private String status;

    private LocalDate createdAt;
}
