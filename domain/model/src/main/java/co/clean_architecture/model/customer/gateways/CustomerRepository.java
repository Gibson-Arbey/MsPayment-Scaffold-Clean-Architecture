package co.clean_architecture.model.customer.gateways;

import co.clean_architecture.model.customer.Customer;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> findById(Long customerId);
}
