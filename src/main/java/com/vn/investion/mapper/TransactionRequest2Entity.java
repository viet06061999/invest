package com.vn.investion.mapper;

import com.vn.investion.dto.transaction.TransactionRequest;
import com.vn.investion.model.TransactionHis;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionRequest2Entity extends BeanMapper<TransactionRequest, TransactionHis>{
    TransactionRequest2Entity INSTANCE = Mappers.getMapper(TransactionRequest2Entity.class);
}
