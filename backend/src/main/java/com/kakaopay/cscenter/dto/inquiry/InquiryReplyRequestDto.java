package com.kakaopay.cscenter.dto.inquiry;

import com.kakaopay.cscenter.domain.inquiry.InquiryReply;
import com.kakaopay.cscenter.dto.BaseRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InquiryReplyRequestDto implements BaseRequestDto {

    @NotNull(message = "문의글 id 값이 누락되었습니다.")
    private Long inquiryId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Override
    public InquiryReply toEntity() {
        return InquiryReplyMapStruct.INSTANCE.toEntity(this);
    }
}
