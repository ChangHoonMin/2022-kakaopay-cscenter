package com.kakaopay.cscenter.service.inquiry;

import com.kakaopay.cscenter.domain.inquiry.Inquiry;
import com.kakaopay.cscenter.domain.inquiry.InquiryReply;
import com.kakaopay.cscenter.domain.inquiry.InquiryReplyRepository;
import com.kakaopay.cscenter.dto.inquiry.InquiryReplyRequestDto;
import com.kakaopay.cscenter.dto.inquiry.InquiryReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryReplyServiceImpl implements InquiryReplyService {

    private final InquiryService inquiryService;
    private final InquiryReplyRepository inquiryReplyRepository;

    @Override
    @Transactional
    public InquiryReplyResponseDto save(InquiryReplyRequestDto inquiryReplyRequestDto, Long counselorId) {
        Inquiry inquiry = inquiryService.findEntityById(inquiryReplyRequestDto.getInquiryId())
                .counselorIdValidation(counselorId);
        InquiryReply inquiryReply = inquiryReplyRequestDto.toEntity().inquiry(inquiry);
        return inquiryReplyRepository.save(inquiryReply).toDto();
    }
}
