package com.kakaopay.cscenter.security;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.dto.payload.CounselorPayload;
import com.kakaopay.cscenter.jwt.TokenManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TokenAuthenticationProvider {

    private final TokenManager tokenManager;

    public TokenAuthenticationProvider(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public Authentication authenticate(String token) {
        CounselorPayload payload = tokenManager.extractPayload(token, CounselorPayload.class);
        if (payload != null) {
            Counselor userDetails = payload.toEntity();
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }
        return null;
    }
}
