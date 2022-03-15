package com.kakaopay.cscenter.dto;

import com.kakaopay.cscenter.domain.BaseEntity;

public interface BaseRequestDto {
    BaseEntity toEntity();
}
