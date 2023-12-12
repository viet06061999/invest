package com.vn.investion.mapper;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2UserResponse extends BeanMapper<User, UserResponse>{
    Entity2UserResponse INSTANCE = Mappers.getMapper(Entity2UserResponse.class);
}
