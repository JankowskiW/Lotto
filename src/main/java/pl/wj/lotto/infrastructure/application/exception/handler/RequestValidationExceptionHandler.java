package pl.wj.lotto.infrastructure.application.exception.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.wj.lotto.infrastructure.application.exception.body.RequestValidationExceptionBody;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class RequestValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestValidationExceptionBody handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(String.format("[%s] MethodArgumentNotValidException has been thrown", HttpStatus.BAD_REQUEST.value()));
        return new RequestValidationExceptionBody(
                extractMessagesFromException(e),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());
    }

    private List<String> extractMessagesFromException(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
