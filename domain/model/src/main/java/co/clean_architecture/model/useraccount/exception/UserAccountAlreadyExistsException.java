package co.clean_architecture.model.useraccount.exception;

import co.clean_architecture.model.exception.DomainException;

public class UserAccountAlreadyExistsException extends DomainException {
    public UserAccountAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "USER_ACCOUNT_ALREADY_EXISTS";
    }
}
