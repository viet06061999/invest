package com.vn.investion.mapper;

import com.vn.investion.dto.auth.NotificationResponse;
import com.vn.investion.model.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2NotificationResponse extends BeanMapper<UserNotification, NotificationResponse> {
    Entity2NotificationResponse INSTANCE = Mappers.getMapper(Entity2NotificationResponse.class);
}
