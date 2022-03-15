package com.kakaopay.cscenter.service.inquiry;

import com.kakaopay.cscenter.domain.inquiry.Inquiry;
import com.kakaopay.cscenter.dto.inquiry.InquiryRequestDto;
import com.kakaopay.cscenter.dto.inquiry.InquiryResponseDto;

import java.util.List;

public interface InquiryService {
    List<InquiryResponseDto> findAll();
    InquiryResponseDto save(InquiryRequestDto inquiryRequestDto);
    Inquiry findEntityById(Long id);
    InquiryResponseDto findById(Long id);
    List<InquiryResponseDto> findByCounselorIsNull();
    InquiryResponseDto updateCounselor(Long id, Long counselorId);
}
