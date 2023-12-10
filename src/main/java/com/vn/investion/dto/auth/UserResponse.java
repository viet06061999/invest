package com.vn.investion.dto.auth;

import com.vn.investion.model.define.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse  {
    private Long id;
    private String firstname;
    private String lastname;
    private String phone;
    private String code;
    private String refId;
    private Double point;
    Role role;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
