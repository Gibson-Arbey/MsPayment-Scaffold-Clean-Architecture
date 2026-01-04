package co.clean_architecture.consumer.customer;

import co.clean_architecture.model.customer.Customer;
import co.clean_architecture.model.customer.gateways.CustomerRepository;
import co.clean_architecture.model.exception.ExternalServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerRestConsumer implements CustomerRepository {

    private final WebClient webClient;

    @Override
    @CircuitBreaker(name = "customer-service" , fallbackMethod = "findByIdFallback")
    public Mono<Customer> findById(Long customerId) {
        return webClient
                .get()
                .uri("/api/v1/customers/{customerId}", customerId)
                .retrieve()
                .onStatus(
                    HttpStatus.NOT_FOUND::equals,
                    response -> Mono.empty()
                )
                .onStatus(HttpStatusCode::is5xxServerError,
                    response -> Mono.error(
                        new ExternalServiceUnavailableException(
                            "Customer service error"
                        )
                    )
                )
                .bodyToMono(CustomerResponse.class)
                .map(this::toDomain);
    }

    private Mono<Customer> findByIdFallback(Long customerId, Throwable throwable) {
        return Mono.error(
                new ExternalServiceUnavailableException(
                        "Customer service unavailable"
                )
        );
    }

    private Customer toDomain(CustomerResponse response) {
        return Customer.builder()
                .id(response.getId())
                .name(response.getName())
                .lastName(response.getLastName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .status(response.getStatus())
                .createdAt(response.getCreatedAt())
                .build();
    }
}
