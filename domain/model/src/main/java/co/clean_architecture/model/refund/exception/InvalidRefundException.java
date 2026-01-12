package co.clean_architecture.model.refund.exception;

import co.clean_architecture.model.exception.DomainException;
import co.clean_architecture.model.exception.ErrorTypeEnum;

public class InvalidRefundException extends DomainException {
    public InvalidRefundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_REFUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
