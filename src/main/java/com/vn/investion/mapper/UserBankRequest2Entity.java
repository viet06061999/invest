package com.vn.investion.mapper;

import com.vn.investion.dto.auth.UserBankRequest;
import com.vn.investion.model.UserBank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserBankRequest2Entity extends BeanMapper<UserBankRequest, UserBank>{
    UserBankRequest2Entity INSTANCE = Mappers.getMapper(UserBankRequest2Entity.class);
}
