package co.clean_architecture.model.exception;

public class ExternalServiceUnavailableException extends DomainException {
    public ExternalServiceUnavailableException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "EXTERNAL_SERVICE_UNAVAILABLE";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.SERVICE_UNAVAILABLE;
    }
}
