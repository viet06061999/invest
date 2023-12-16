package com.vn.investion.dto.auth;

import com.vn.investion.model.level.GiftLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {
    @Schema(description = "Thông tin user")
    private UserResponse user;
    @Schema(description = "Thông tin đội nhóm của user")
    private Map<Integer, List<UserResponse>> myTeams;
    @Schema(description = "Tổng số thành viên của nhóm")
    private Integer totalMember;
    @Schema(description = "Tổng số thành viên f1 của nhóm")
    private Integer totalF1;
    @Schema(description = "Tổng số tiền nạp của nhóm")
    private Long totalDeposit;
    @Schema(description = "Thôn tin level hiện tại của leader")
    private GiftLevel giftLevel;
}
