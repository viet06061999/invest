package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.ipackage.InterestHisResponse;
import com.vn.investion.dto.ipackage.UserLeaderResponse;
import com.vn.investion.dto.ipackage.UserPackageResponse;
import com.vn.investion.service.PackageService;
import com.vn.investion.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserPackageController {
    private final PackageService packageService;

    @PutMapping("/invest/{investPackageId}/subscribe")
    @Operation(description = "User đăng ký gói đầu tư", parameters = {@Parameter(name = "investPackageId", description = "id của gói đầu tư")})
    public Response<UserPackageResponse> jointInvest(Authentication authentication, @PathVariable Long investPackageId) {
        return Response.ofSucceeded(packageService.jointPackage(JwtService.getUserName(authentication), investPackageId));
    }

    @PutMapping("/leader/{leaderPackageId}/subscribe")
    @Operation(description = "User đăng ký gói leader", parameters = {@Parameter(name = "leaderPackageId", description = "id của gói leader")})
    public Response<UserLeaderResponse> jointLeader(Authentication authentication, @PathVariable Long leaderPackageId) {
        return Response.ofSucceeded(packageService.jointLeader(JwtService.getUserName(authentication), leaderPackageId));
    }

    @GetMapping("/user-invests")
    @Operation(description = "Lấy tất cả gói đầu tư của tất cả user: UserPackage")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<List<UserPackageResponse>> getAllUserPackage() {
        return Response.ofSucceeded(packageService.getAllUserPackage());
    }

    @GetMapping("/user-leaders")
    @Operation(description = "Lấy tất cả gói leader của tất cả user: UserLeader")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<List<UserLeaderResponse>> getAllUserLeader() {
        return Response.ofSucceeded(packageService.getAllUserLeader());
    }

    @GetMapping("user/leaders")
    @Operation(description = "Lấy tất cả gói leader của user hiện tại")
    public Response<List<UserLeaderResponse>> getUserLeader(Authentication authentication) {
        return Response.ofSucceeded(packageService.getUserLeaderByPhone(JwtService.getUserName(authentication)));
    }

    @GetMapping("user/invests")
    @Operation(description = "Lấy tất cả gói đầu tư của user hiện tại")
    public Response<List<UserPackageResponse>> getUserPackage(Authentication authentication) {
        return Response.ofSucceeded(packageService.getUserPackageByPhone(JwtService.getUserName(authentication)));
    }

    @PutMapping("/user-invests/{userInvestId}/withdraw-interest")
    @Operation(description = "Rút lãi gói đầu tư", parameters = {@Parameter( name = "userInvestId", description = "Id lấy từ api /user/invests. User rút lãi gói đầu tư, lãi tính từ ngày rút lãi cuối cùng cho đến hiện tại")})
    public Response<UserPackageResponse> withdrawInvestInt(Authentication authentication, @PathVariable Long userInvestId) {
        return Response.ofSucceeded(packageService.withdrawIntInvest(userInvestId, JwtService.getUserName(authentication)));
    }

    @PutMapping("/user-leaders/{userLeaderId}/withdraw-interest")
    @Operation(description = "Rút lãi gói leader", parameters = {@Parameter(name = "userLeaderId",description = "Id lấy từ api /user/leaders. User rút lãi gói leader, lãi tính từ ngày rút lãi cuối cùng cho đến hiện tại")})
    public Response<UserLeaderResponse> withdrawLeaderInt(Authentication authentication, @PathVariable Long userLeaderId) {
        return Response.ofSucceeded(packageService.withdrawIntLeader(userLeaderId, JwtService.getUserName(authentication)));
    }

    @PutMapping("/user-invests/{userInvestId}/withdraw")
    @Operation(description = "Rút lãi gói đầu tư", parameters = {@Parameter(name = "userInvestId",description = "Id lấy từ api /user/leaders. User vốn gói leader, hoàn trả lãi và vốn cho user, kết thúc gói đầu tư")})
    public Response<UserPackageResponse> withdrawInvest(Authentication authentication, @PathVariable Long userInvestId) {
        return Response.ofSucceeded(packageService.withdrawInvest(userInvestId, JwtService.getUserName(authentication)));
    }

    @PutMapping("/user-leaders/{userLeaderId}/withdraw")
    @Operation(description = "Rút lãi gói leader", parameters = {@Parameter(name = "userLeaderId",description = "Id lấy từ api /user/leaders. User vốn gói leader, hoàn trả lãi và vốn cho user, kết thúc gói leader")})
    public Response<UserLeaderResponse> withdrawLeader(Authentication authentication, @PathVariable Long userLeaderId) {
        return Response.ofSucceeded(packageService.withdrawLeader(userLeaderId, JwtService.getUserName(authentication)));
    }

    @GetMapping("user/invest-his")
    @Operation(description = "Lấy lịch sử rút tiền gói đầu tư của user")
    public Response<List<InterestHisResponse>> getIntHis(Authentication authentication) {
        return Response.ofSucceeded(packageService.getIntHisUser(JwtService.getUserName(authentication)));
    }
}
