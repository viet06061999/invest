package com.vn.investion.mapper;

import com.vn.investion.dto.auth.UserBankResponse;
import com.vn.investion.model.UserBank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2UserBankResponse extends BeanMapper<UserBank, UserBankResponse>{
    Entity2UserBankResponse INSTANCE = Mappers.getMapper(Entity2UserBankResponse.class);
}
