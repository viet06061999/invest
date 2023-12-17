package com.vn.investion.dto.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateUserRequest {
    String firstname;
    String lastname;
    String password;
    String identification;
}
