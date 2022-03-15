package com.kakaopay.cscenter.error.enums;

import com.kakaopay.cscenter.error.exception.KakaoPayException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InquiryErrorEnum {

    NOT_FOUND(new KakaoPayException("존재하지 않는 문의건 입니다.", HttpStatus.NOT_FOUND)),
    CONFLICT(new KakaoPayException("이미 담당자가 지정된 문의건 입니다.", HttpStatus.CONFLICT)),
    ;

    private final KakaoPayException exception;
}
