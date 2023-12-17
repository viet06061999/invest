package com.vn.investion.dto.auth;

import com.vn.investion.model.define.Role;
import com.vn.investion.model.define.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String phone;
    @Schema(description = "Mã mời là code của user khác")
    private String code;
    @Schema(description = "Mã người giới thiệu")
    private String refId;
    @Schema(description = "Vốn đầu tư khả dụng của user")
    private Double depositBalance;
    @Schema(description = "Doanh thu đầu tư của user")
    private Double availableBalance;
    private UserStatus status;
    private String identification;
    Role role;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
