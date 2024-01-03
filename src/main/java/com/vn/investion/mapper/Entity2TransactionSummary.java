package com.vn.investion.mapper;

import com.vn.investion.dto.dashboard.TransactionSummary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface Entity2TransactionSummary extends BeanMapper<Map<String, BigDecimal>, TransactionSummary>{
    Entity2TransactionSummary INSTANCE = Mappers.getMapper(Entity2TransactionSummary.class);
}
