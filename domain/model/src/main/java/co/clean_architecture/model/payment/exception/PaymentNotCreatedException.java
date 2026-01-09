package co.clean_architecture.model.payment.exception;

import co.clean_architecture.model.exception.DomainException;
import co.clean_architecture.model.exception.ErrorTypeEnum;

public class PaymentNotCreatedException extends DomainException {
    public PaymentNotCreatedException(String message) {
        super(message);
    }

  @Override
  public String getCode() {
    return "PAYMENT_NOT_CREATED";
  }

  @Override
  public ErrorTypeEnum getErrorType() {
    return ErrorTypeEnum.VALIDATION;
  }
}
