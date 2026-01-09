package co.clean_architecture.usecase.payment.exception;

import co.clean_architecture.model.exception.DomainException;
import co.clean_architecture.model.exception.ErrorTypeEnum;

public class StatusPaymentNotValidException extends DomainException {
    public StatusPaymentNotValidException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "STATUS_PAYMENT_NOT_VALID";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.BUSINESS_RULE;
    }
}
