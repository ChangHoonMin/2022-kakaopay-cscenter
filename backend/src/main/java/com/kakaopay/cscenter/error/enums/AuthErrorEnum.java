package com.kakaopay.cscenter.error.enums;

import com.kakaopay.cscenter.error.exception.KakaoPayException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorEnum {

    NOT_FOUND(new KakaoPayException("아이디 또는 비밀번호가 일치 하지 않습니다.", HttpStatus.NOT_FOUND))
    ;

    private final KakaoPayException exception;
}
