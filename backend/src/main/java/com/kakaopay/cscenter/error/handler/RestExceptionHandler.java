package com.kakaopay.cscenter.error.handler;

import com.kakaopay.cscenter.dto.api.AbstractApiResponseDto;
import com.kakaopay.cscenter.error.exception.KakaoPayException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class
    })
    protected ResponseEntity<?> handleBadRequest(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }
        return errorResponse(HttpStatus.BAD_REQUEST, FieldErrorDetail.getList(bindingResult));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class
    })
    protected ResponseEntity<?> handleNotFound(Exception e) {
        log.warn("[handleNotFound] : {}", e.getMessage());
        return errorResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(KakaoPayException.class)
    protected ResponseEntity<?> handleKakaoPayException(KakaoPayException e) {
        HttpStatus status = Optional.ofNullable(e.getStatus()).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        String message = Optional.ofNullable(e.getMessage()).orElse(status.getReasonPhrase());
        if (e.getCause() != null) {
            log.error("[handleKakaoPayException] get Cause Message : {}", e.getMessage());
        }
        return errorResponse(status.value(), message, null, status);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleAuthenticationException() {
        return errorResponse(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<?> handleThrowable(Throwable t) {
        log.error("[handleThrowable] : ", t);
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<?> errorResponse(HttpStatus status) {
        return errorResponse(status, null);
    }

    protected <T> ResponseEntity<?> errorResponse(HttpStatus status, T errors) {
        return errorResponse(status.value(), status.getReasonPhrase(), errors, status);
    }

    protected <T> ResponseEntity<?> errorResponse(Integer code, String message, T errors, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorApiResponseDto<>(code, message, errors));
    }

    @Getter
    private static class FieldErrorDetail {

        private final String field;
        private final String message;

        FieldErrorDetail(FieldError fieldError) {
            this.field = fieldError.getField();
            this.message = fieldError.getDefaultMessage();
        }

        static List<FieldErrorDetail> getList(BindingResult bindingResult) {
            return Optional.ofNullable(bindingResult)
                    .map(Errors::getFieldErrors)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(FieldErrorDetail::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    private static class ErrorApiResponseDto<T> extends AbstractApiResponseDto {

        private final T errors;

        ErrorApiResponseDto(Integer code, String message, T errors) {
            super(code, message);
            this.errors = errors;
        }
    }
}