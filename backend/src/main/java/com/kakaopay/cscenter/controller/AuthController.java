package com.kakaopay.cscenter.controller;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.dto.api.SuccessApiResponseDto;
import com.kakaopay.cscenter.dto.counselor.CounselorLoginRequestDto;
import com.kakaopay.cscenter.service.counselor.CounselorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final CounselorService counselorService;

    @PostMapping("/auth/authorize")
    public ResponseEntity<?> authorize(@RequestBody @Valid CounselorLoginRequestDto counselorLoginRequestDto) {
        return SuccessApiResponseDto.entity(counselorService.authGenerateToken(counselorLoginRequestDto));
    }
}