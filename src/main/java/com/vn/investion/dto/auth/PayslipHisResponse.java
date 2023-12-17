package com.vn.investion.dto.auth;

import com.vn.investion.model.define.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayslipHisResponse extends AuditEntity {

    private Integer id;
    private Long amount;
    private UserResponse user;
    private Integer totalMember;
    private Integer totalF1;
    private Long totalDeposit;
    private Integer level;
    private double progress;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
