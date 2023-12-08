package dev.sabri.securityjwt.mapper;

import dev.sabri.securityjwt.controller.dto.UserResponse;
import dev.sabri.securityjwt.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2AcountResponse  extends BeanMapper<User, UserResponse>{
    Entity2AcountResponse INSTANCE = Mappers.getMapper(Entity2AcountResponse.class);
}
