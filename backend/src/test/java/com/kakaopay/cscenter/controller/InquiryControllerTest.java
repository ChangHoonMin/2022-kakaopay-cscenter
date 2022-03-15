package com.kakaopay.cscenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.cscenter.dto.inquiry.InquiryResponseDto;
import com.kakaopay.cscenter.error.enums.InquiryErrorEnum;
import com.kakaopay.cscenter.jwt.TokenManager;
import com.kakaopay.cscenter.service.inquiry.InquiryReplyService;
import com.kakaopay.cscenter.service.inquiry.InquiryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InquiryController.class)
public class InquiryControllerTest {

    @MockBean
    InquiryService inquiryService;

    @MockBean
    InquiryReplyService inquiryReplyService;

    @MockBean
    TokenManager tokenManager;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("아이디로 문의글 조회 한다.")
    void find_inquiry_by_id() throws Exception {
        Long id = 1L;
        InquiryResponseDto inquiryResponseDto = InquiryResponseDto.builder()
                .id(1L)
                .title("안녕하세요.")
                .content("내용입니다.")
                .createdDate(LocalDateTime.now())
                .build();
        when(inquiryService.findById(id)).thenReturn(inquiryResponseDto);

        mockMvc.perform(get("/api/v1/inquiries/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("아이디로 문의글 조회 파라미터가 없으면 ErrorResponseApiDto 400으로 리턴한다.")
    void inquiry_id_blank_bad_request_test() throws Exception {
        mockMvc.perform(get("/api/v1/inquiries/ "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("아이디로 문의글 조회 파라미터가 타입이 안맞으면 ErrorResponseApiDto 400으로 리턴한다.")
    void inquiry_id_type_miss_bad_request_test() throws Exception {
        mockMvc.perform(get("/api/v1/inquiries/aaaaaa"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andDo(MockMvcResultHandlers.print());

    }
}
