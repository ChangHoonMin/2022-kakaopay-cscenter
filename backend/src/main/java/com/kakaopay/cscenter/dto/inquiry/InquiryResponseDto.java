package com.kakaopay.cscenter.dto.inquiry;

import com.kakaopay.cscenter.dto.BaseResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class InquiryResponseDto implements BaseResponseDto {

    private Long id;
    private String title;
    private String content;
    private InquiryReplyResponseDto reply;
    private String createdDate;

    @Builder
    protected InquiryResponseDto(Long id, String title, String content, InquiryReplyResponseDto inquiryReply, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reply = inquiryReply;
        this.createdDate = BaseResponseDto.toStringDateTime(createdDate);
    }
}
