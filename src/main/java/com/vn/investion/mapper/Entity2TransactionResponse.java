package com.vn.investion.mapper;

import com.vn.investion.dto.transaction.TransactionResponse;
import com.vn.investion.model.TransactionHis;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2TransactionResponse extends BeanMapper<TransactionHis, TransactionResponse>{
    Entity2TransactionResponse INSTANCE = Mappers.getMapper(Entity2TransactionResponse.class);
}
