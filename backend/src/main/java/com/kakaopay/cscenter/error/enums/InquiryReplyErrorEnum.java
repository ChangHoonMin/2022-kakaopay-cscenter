package com.kakaopay.cscenter.error.enums;

import com.kakaopay.cscenter.error.exception.KakaoPayException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InquiryReplyErrorEnum {

    FORBIDDEN(new KakaoPayException("답변 권한이 없습니다.", HttpStatus.FORBIDDEN)),
    CONFLICT(new KakaoPayException("답변이 완료된 건입니다.", HttpStatus.CONFLICT))
    ;

    private final KakaoPayException exception;
}
