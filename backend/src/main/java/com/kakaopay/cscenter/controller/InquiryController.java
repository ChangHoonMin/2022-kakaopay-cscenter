package com.kakaopay.cscenter.controller;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.dto.api.SuccessApiResponseDto;
import com.kakaopay.cscenter.dto.inquiry.InquiryReplyRequestDto;
import com.kakaopay.cscenter.dto.inquiry.InquiryRequestDto;
import com.kakaopay.cscenter.service.inquiry.InquiryReplyService;
import com.kakaopay.cscenter.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final InquiryReplyService inquiryReplyService;

    /**
     * 전체 가져오기
     * @return
     */
    @GetMapping("/inquiries")
    public ResponseEntity<?> all() {
        return SuccessApiResponseDto.entity(inquiryService.findAll());
    }

    /**
     * 문의 내용 가져오기
     * @param id
     * @return
     */
    @GetMapping("/inquiries/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        return SuccessApiResponseDto.entity(inquiryService.findById(id));
    }

    /**
     * 고객 아이디로 리스트 가져오기
     * @param id
     * @return
     */
    @GetMapping("/inquiries/customers/{id}")
    public ResponseEntity<?> byCustomerId(@PathVariable String id) {
        return SuccessApiResponseDto.entity(inquiryService.findByCustomerId(id));
    }

    /**
     * 고객 문의 등록
     * @param inquiryRequestDto
     * @return
     */
    @PostMapping("/inquiries")
    public ResponseEntity<?> save(@RequestBody @Valid InquiryRequestDto inquiryRequestDto) {
        return SuccessApiResponseDto.entity(inquiryService.save(inquiryRequestDto));
    }

    /**
     * 고객 문의 상담사 지정
     * @param id
     * @param counselor
     * @return
     */
    @PatchMapping("/inquiries/{id}/counselors")
    public ResponseEntity<?> updateCounselor(@PathVariable Long id, @AuthenticationPrincipal Counselor counselor) {
        return SuccessApiResponseDto.entity(inquiryService.updateCounselor(id, counselor.getId()));
    }

    /**
     * 미답변 문의 내용 전체 가져오기
     * @return
     */
    @GetMapping("/inquiries/no-replies")
    public ResponseEntity<?> byInquiryReplyIsNull() {
        return SuccessApiResponseDto.entity(inquiryService.findByInquiryReplyIsNull());
    }

    /**
     * 고객 문의 답변 등록
     * @param inquiryReplyRequestDto
     * @param counselor
     * @return
     */
    @PostMapping("/inquiries/replies")
    public ResponseEntity<?> save(@RequestBody @Valid InquiryReplyRequestDto inquiryReplyRequestDto, @AuthenticationPrincipal Counselor counselor) {
        return SuccessApiResponseDto.entity(inquiryReplyService.save(inquiryReplyRequestDto, counselor.getId()));
    }
}
