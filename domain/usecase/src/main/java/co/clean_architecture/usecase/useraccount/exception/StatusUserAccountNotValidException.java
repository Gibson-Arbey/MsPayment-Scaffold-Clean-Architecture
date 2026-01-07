package co.clean_architecture.usecase.useraccount.exception;

import co.clean_architecture.model.exception.DomainException;
import co.clean_architecture.model.exception.ErrorTypeEnum;

public class StatusUserAccountNotValidException extends DomainException {
    public StatusUserAccountNotValidException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "STATUS_USER_ACCOUNT_NOT_VALID";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.BUSINESS_RULE;
    }
}
