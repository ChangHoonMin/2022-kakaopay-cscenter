package com.kakaopay.cscenter.dto;

import com.kakaopay.cscenter.domain.BaseEntity;

import java.util.List;

public interface BaseMapStruct<E extends BaseEntity, REQ extends BaseRequestDto, RES extends BaseResponseDto> {
    E toEntity(REQ dto);
    RES toDto(E entity);
    List<RES> toDtoList(List<E> entityList);
}