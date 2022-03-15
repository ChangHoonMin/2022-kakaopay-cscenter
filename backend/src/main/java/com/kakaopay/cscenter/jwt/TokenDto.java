package com.kakaopay.cscenter.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDto {

    private String accessToken;
    private Long accessTokenExpiration;

    @Builder
    private TokenDto(String accessToken, Long accessTokenExpiration) {
        this.accessToken = accessToken;
        this.accessTokenExpiration = accessTokenExpiration;
    }
}
