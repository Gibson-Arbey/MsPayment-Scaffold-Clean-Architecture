package co.clean_architecture.model.payment.exception;

import co.clean_architecture.model.exception.DomainException;
import co.clean_architecture.model.exception.ErrorTypeEnum;

public class InvalidPaymentException extends DomainException {
    public InvalidPaymentException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_PAYMENT";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
