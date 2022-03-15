package com.kakaopay.cscenter.dto.payload;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.jwt.Payload;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CounselorPayload implements Payload {

    private Long id;
    private String username;
    private String name;

    @Builder
    private CounselorPayload(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Counselor toEntity() {
        return Counselor.builder()
                .id(this.id)
                .username(this.username)
                .name(this.name)
                .build();
    }
}
