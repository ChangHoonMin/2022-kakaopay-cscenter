package com.kakaopay.cscenter.error.enums;

import com.kakaopay.cscenter.error.exception.KakaoPayException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CounselorErrorEnum {

    NOT_FOUND(new KakaoPayException("존재하지 않는 상담사 입니다.", HttpStatus.NOT_FOUND)),
    ;

    private final KakaoPayException exception;
}
