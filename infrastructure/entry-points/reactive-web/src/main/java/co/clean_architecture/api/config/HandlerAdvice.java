package co.clean_architecture.api.config;

import co.clean_architecture.api.dto.ErrorResponse;
import co.clean_architecture.model.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class HandlerAdvice {

    @ExceptionHandler(DomainException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDomainException(DomainException ex) {
        return Mono.just(
            ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                    .code(ex.getCode())
                    .message(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            )
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneric(Exception ex) {
        log.error("Unhandled error", ex);
        return Mono.just(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ErrorResponse.builder()
                        .code("INTERNAL_ERROR")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                )
        );
    }
}
