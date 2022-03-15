package com.kakaopay.cscenter.service.inquiry;

import com.kakaopay.cscenter.domain.inquiry.Inquiry;
import com.kakaopay.cscenter.domain.inquiry.InquiryRepository;
import com.kakaopay.cscenter.dto.inquiry.*;
import com.kakaopay.cscenter.error.enums.InquiryErrorEnum;
import com.kakaopay.cscenter.service.counselor.CounselorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final CounselorService counselorService;

    @Override
    public List<InquiryResponseDto> findAll() {
        return InquiryMapStruct.INSTANCE.toDtoList(inquiryRepository.findAll());
    }

    @Override
    @Transactional
    public InquiryResponseDto save(InquiryRequestDto inquiryRequestDto) {
        return inquiryRepository.save(inquiryRequestDto.toEntity()).toDto();
    }

    @Override
    public Inquiry findEntityById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(InquiryErrorEnum.NOT_FOUND::getException);
    }

    @Override
    @Transactional(readOnly = true)
    public InquiryResponseDto findById(Long id) {
        return findEntityById(id).toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> findByCounselorIsNull() {
        return InquiryMapStruct.INSTANCE.toDtoList(inquiryRepository.findByCounselorIsNull());
    }

    @Override
    @Transactional
    public InquiryResponseDto updateCounselor(Long id, Long counselorId) {
        return findEntityById(id)
                .updateCounselor(counselorService.findEntityById(counselorId))
                .toDto();
    }
}
