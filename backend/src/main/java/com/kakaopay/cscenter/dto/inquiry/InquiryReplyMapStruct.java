package com.kakaopay.cscenter.dto.inquiry;

import com.kakaopay.cscenter.domain.inquiry.InquiryReply;
import com.kakaopay.cscenter.dto.BaseMapStruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InquiryReplyMapStruct extends BaseMapStruct<InquiryReply, InquiryReplyRequestDto, InquiryReplyResponseDto> {
    InquiryReplyMapStruct INSTANCE = Mappers.getMapper(InquiryReplyMapStruct.class);
}
