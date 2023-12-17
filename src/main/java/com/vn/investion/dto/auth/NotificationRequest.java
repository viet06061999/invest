package com.vn.investion.dto.auth;

import com.vn.investion.model.define.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationRequest {
    @NotBlank
    @Schema(description = "Tiêu đề", example = "Nâng cấp hệ thống")
    String title;
    @Schema(description = "Nội dung thông báo", example = "Hệ thống đang nâng cấp")
    String description;
    @Schema(description = "Đối tượng của thông báo(ID của các đối tượng)", example = "12")
    String object;
    @Schema(description = "Loại thông báo", example = "SYSTEM", allowableValues = {"INVEST", "LEADER", "USER_REFERENCE", "SYSTEM"})
    NotificationType type;
    @Schema(description = "User thông báo", example = "null nếu thông báo all")
    Long userId;
}
