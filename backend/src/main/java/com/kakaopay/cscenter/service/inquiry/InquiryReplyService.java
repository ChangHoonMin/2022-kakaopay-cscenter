package com.kakaopay.cscenter.service.inquiry;

import com.kakaopay.cscenter.dto.inquiry.InquiryReplyRequestDto;
import com.kakaopay.cscenter.dto.inquiry.InquiryReplyResponseDto;

public interface InquiryReplyService {
    InquiryReplyResponseDto save(InquiryReplyRequestDto inquiryReplyRequestDto, Long counselorId);
}