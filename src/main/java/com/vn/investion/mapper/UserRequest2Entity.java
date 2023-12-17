package com.vn.investion.mapper;

import com.vn.investion.dto.auth.UpdateUserRequest;
import com.vn.investion.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRequest2Entity extends BeanMapper<UpdateUserRequest, User>{
    UserRequest2Entity INSTANCE = Mappers.getMapper(UserRequest2Entity.class);
}
