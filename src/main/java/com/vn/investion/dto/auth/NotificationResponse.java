package com.vn.investion.dto.auth;

import com.vn.investion.model.define.NotificationStatus;
import com.vn.investion.model.define.NotificationType;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class NotificationResponse {
    Long id;
    String title;
    String description;
    String object;
    NotificationType type;
    NotificationStatus status;
    UserResponse user;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
