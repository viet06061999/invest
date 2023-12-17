package com.vn.investion.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "First name is mandatory")
    String firstname;
    @NotBlank(message = "Last name is mandatory")
    String lastname;
    @NotBlank(message = "Phone is mandatory")
    String phone;
    @NotBlank(message = "Password is mandatory")
    String password;
    @NotBlank(message = "Reference id is mandatory")
    @Schema(description = "Mã mời là code của user khác")
    String refId;
    @Schema(description = "Ảnh căn cước")
    String identification;
}
