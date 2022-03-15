package com.kakaopay.cscenter.service.counselor;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.dto.counselor.CounselorLoginRequestDto;
import com.kakaopay.cscenter.jwt.TokenDto;

public interface CounselorService {
    Counselor findEntityById(Long id);
    TokenDto authGenerateToken(CounselorLoginRequestDto counselorLoginRequestDto);
}
