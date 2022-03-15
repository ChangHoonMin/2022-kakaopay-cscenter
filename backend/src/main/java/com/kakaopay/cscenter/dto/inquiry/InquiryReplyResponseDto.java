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
public class InquiryReplyResponseDto implements BaseResponseDto {

    private Long id;
    private String content;
    private String createdDate;

    @Builder
    private InquiryReplyResponseDto(Long id, String content, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.createdDate = BaseResponseDto.toStringDateTime(createdDate);
    }
}
