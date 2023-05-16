package pl.wj.lotto.infrastructure.application.exception.definition;

public class NumbersValidationException extends RuntimeException {
    public NumbersValidationException(String message) {
        super(message);
    }
}
