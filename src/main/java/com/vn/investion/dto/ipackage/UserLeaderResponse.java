package com.vn.investion.dto.ipackage;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.model.define.InvestType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLeaderResponse {
    Long id;
    Integer duration;
    Double amt;
    InvestType investType;
    Double rate;
    OffsetDateTime interestDate;
    OffsetDateTime withdrawDate;
    UserResponse user;
    LeaderPackageResponse leaderPackage;
    Double currentInterest;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}