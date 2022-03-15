package com.kakaopay.cscenter.dto.inquiry;

import com.kakaopay.cscenter.domain.inquiry.Inquiry;
import com.kakaopay.cscenter.dto.BaseMapStruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InquiryMapStruct extends BaseMapStruct<Inquiry, InquiryRequestDto, InquiryResponseDto> {
    InquiryMapStruct INSTANCE = Mappers.getMapper(InquiryMapStruct.class);
}
