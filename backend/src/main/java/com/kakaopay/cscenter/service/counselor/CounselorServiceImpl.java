package com.kakaopay.cscenter.service.counselor;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.domain.counselor.CounselorRepository;
import com.kakaopay.cscenter.dto.counselor.CounselorLoginRequestDto;
import com.kakaopay.cscenter.error.enums.AuthErrorEnum;
import com.kakaopay.cscenter.error.enums.CounselorErrorEnum;
import com.kakaopay.cscenter.jwt.TokenDto;
import com.kakaopay.cscenter.jwt.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CounselorServiceImpl implements CounselorService {

    private final TokenManager tokenManager;
    private final CounselorRepository counselorRepository;

    @Override
    public Counselor findEntityById(Long id) {
        return counselorRepository.findById(id)
                .orElseThrow(CounselorErrorEnum.NOT_FOUND::getException);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenDto authGenerateToken(CounselorLoginRequestDto counselorLoginRequestDto) {
        Counselor counselor = counselorRepository.findByUsername(counselorLoginRequestDto.getUsername())
                .orElseThrow(AuthErrorEnum.NOT_FOUND::getException);
        if (!counselor.getPassword().equals(counselorLoginRequestDto.getPassword())) {
            throw AuthErrorEnum.NOT_FOUND.getException();
        }
        return tokenManager.generateToken(counselor.toPayload());
    }
}
