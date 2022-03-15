package com.kakaopay.cscenter.error.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class KakaoPayException extends RuntimeException {

    private final HttpStatus status;

    public KakaoPayException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @Builder
    private KakaoPayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
