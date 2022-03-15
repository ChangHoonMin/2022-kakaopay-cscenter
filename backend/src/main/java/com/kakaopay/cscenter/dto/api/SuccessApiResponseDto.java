package com.kakaopay.cscenter.dto.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SuccessApiResponseDto<T> extends AbstractApiResponseDto {

    private final T data;

    private SuccessApiResponseDto(T data) {
        super(HttpStatus.OK);
        this.data = data;
    }

    public static <T> ResponseEntity<?> entity(T data) {
        return ResponseEntity.ok(new SuccessApiResponseDto<>(data));
    }
}
