package com.vn.investion.mapper;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.model.User;
import org.mapstruct.factory.Mappers;

public interface Entity2AcountResponse  extends BeanMapper<User, UserResponse>{
    Entity2AcountResponse INSTANCE = Mappers.getMapper(Entity2AcountResponse.class);
}
