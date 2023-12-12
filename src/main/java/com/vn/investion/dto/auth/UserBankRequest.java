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
public class UserBankRequest {
    @NotBlank
    @Schema(description = "Tên ngân hàng", example = "MB Bank")
    private String bank;

    @Schema(description = "Số tài khoản", example = "9999999060699")
    private String numberAccount;

    @Schema(description = "Tên tài khoản", example = "NGUYEN VAN A")
    private String accountName;
}
