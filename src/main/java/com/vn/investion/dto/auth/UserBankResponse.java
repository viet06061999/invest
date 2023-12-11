package com.vn.investion.dto.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserBankResponse {
    private Long id;
    private UserResponse user;
    private String bank;
    private String numberAccount;
}
