package com.vn.investion.mapper;

import com.vn.investion.dto.dashboard.UserSummary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface Entity2UserSummary extends BeanMapper<Map<String, Long>, UserSummary>{
    Entity2UserSummary INSTANCE = Mappers.getMapper(Entity2UserSummary.class);
}
