package com.kakaopay.cscenter.dto.inquiry;

import com.kakaopay.cscenter.domain.inquiry.Inquiry;
import com.kakaopay.cscenter.dto.BaseRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InquiryRequestDto implements BaseRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String customerId;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 20, message = "제목은 최대 20글자까지만 가능합니다.")
    private String title;

    @NotBlank(message = "문의 내용을 입력해주세요.")
    @Size(max = 2000, message = "내용은 최대 2000글자까지만 가능합니다.")
    private String content;

    @Override
    public Inquiry toEntity() {
        return InquiryMapStruct.INSTANCE.toEntity(this);
    }
}
