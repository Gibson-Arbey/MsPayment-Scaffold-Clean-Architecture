package co.clean_architecture.model.customer.exception;

import co.clean_architecture.model.exception.DomainException;

public class CustomerNotExistsException extends DomainException {
    public CustomerNotExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "CUSTOMER_NOT_EXISTS";
    }
}
