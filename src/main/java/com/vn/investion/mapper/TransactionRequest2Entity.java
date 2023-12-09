package com.vn.investion.mapper;

import com.vn.investion.dto.transaction.TransactionRequest;
import com.vn.investion.model.TransactionHis;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionRequest2Entity extends BeanMapper<TransactionRequest, TransactionHis>{
    TransactionRequest2Entity INSTANCE = Mappers.getMapper(TransactionRequest2Entity.class);
}
