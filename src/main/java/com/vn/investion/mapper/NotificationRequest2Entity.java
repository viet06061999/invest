package com.vn.investion.mapper;

import com.vn.investion.dto.auth.NotificationRequest;
import com.vn.investion.model.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationRequest2Entity extends BeanMapper<NotificationRequest, UserNotification>{
    NotificationRequest2Entity INSTANCE = Mappers.getMapper(NotificationRequest2Entity.class);
}
