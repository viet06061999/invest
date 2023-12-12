package com.vn.investion.dto.auth;

import com.vn.investion.model.define.ReportStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReportResponse {
    private Long id;
    private UserResponse user;
    private String report;
    private String attach;
    private ReportStatus status;
    private String title;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
