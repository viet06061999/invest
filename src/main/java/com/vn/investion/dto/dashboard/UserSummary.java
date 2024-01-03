package com.vn.investion.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    @Schema(description = "Tổng số user")
    private Long totalUsers;
    @Schema(description = "Tổng số user pending")
    private Long countPending;
    @Schema(description = "Tổng số user verified")
    private Long countVerified;
    @Schema(description = "Tổng số user unverified")
    private Long countUnverified;
    @Schema(description = "Tổng số user inactive")
    private Long countInactive;
}
