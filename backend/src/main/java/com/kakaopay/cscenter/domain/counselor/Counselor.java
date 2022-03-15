package com.kakaopay.cscenter.domain.counselor;

import com.kakaopay.cscenter.domain.BaseEntity;
import com.kakaopay.cscenter.dto.BaseResponseDto;
import com.kakaopay.cscenter.dto.payload.CounselorPayload;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
public class Counselor extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Builder
    private Counselor(Long id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public BaseResponseDto toDto() {
        return null;
    }

    public CounselorPayload toPayload() {
        return CounselorPayload.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .build();
    }
}
