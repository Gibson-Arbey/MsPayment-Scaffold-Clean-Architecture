package co.clean_architecture.model.useraccount.exception;

import co.clean_architecture.model.exception.DomainException;
import co.clean_architecture.model.exception.ErrorTypeEnum;

public class InvalidBalanceOperationException extends DomainException {
    public InvalidBalanceOperationException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "BALANCE_NOT_VALID";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
