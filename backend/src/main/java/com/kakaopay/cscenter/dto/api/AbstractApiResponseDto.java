package com.kakaopay.cscenter.dto.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractApiResponseDto {

    private final Integer code;
    private final String message;

    public AbstractApiResponseDto(HttpStatus status) {
        this(status.value(), status.getReasonPhrase());
    }

    public AbstractApiResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
