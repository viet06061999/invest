package com.vn.investion.dto.ipackage;

import com.vn.investion.dto.auth.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterestHisResponse {
    Integer id;
    Double amount;
    Double interestAmount;
    UserResponse user;
    LeaderPackageResponse leaderPackage;
    InvestPackageResponse investPackage;
    @Schema(description = "Thông tin của F1")
    UserResponse refUser;
    Double remainAvailableBalance;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
