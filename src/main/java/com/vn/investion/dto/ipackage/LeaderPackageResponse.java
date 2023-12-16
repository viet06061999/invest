package com.vn.investion.dto.ipackage;

import com.vn.investion.model.define.InvestType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaderPackageResponse {
    Integer id;
    Integer duration;
    Long amt;
    InvestType investType;
    Double rate;
    String title;
    String description;
    Long remainBuy;
    Long userCanBuy;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
