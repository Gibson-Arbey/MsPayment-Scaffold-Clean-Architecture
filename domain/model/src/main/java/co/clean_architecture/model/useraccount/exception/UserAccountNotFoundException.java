package co.clean_architecture.model.useraccount.exception;

import co.clean_architecture.model.exception.DomainException;

public class UserAccountNotFoundException extends DomainException {

  public UserAccountNotFoundException(String message) {
        super(message);
    }

  @Override
  public String getCode() {
    return "USER_ACCOUNT_NOT_FOUND";
  }
}
