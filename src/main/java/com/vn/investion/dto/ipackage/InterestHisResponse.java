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
    @Schema(description = "Số vốn rút")
    Double amount;
    @Schema(description = "Số lãi rút")
    Double interestAmount;
    UserResponse user;
    LeaderPackageResponse leaderPackage;
    InvestPackageResponse investPackage;
    @Schema(description = "Thông tin của F1")
    UserResponse refUser;
    @Schema(description = "Doanh thu đầu tư còn lại của user sau giao dịch")
    Double remainAvailableBalance;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
