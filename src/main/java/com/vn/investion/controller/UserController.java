package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.auth.*;
import com.vn.investion.mapper.Entity2UserResponse;
import com.vn.investion.model.define.UserStatus;
import com.vn.investion.repo.UserRepository;
import com.vn.investion.service.UserService;
import com.vn.investion.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService service;
    private final UserRepository repository;

    @GetMapping("/user/banks")
    @Operation(description = "Lấy tất cả tài khoản ngân hàng của user hiện tại")
    public Response<List<UserBankResponse>> getUserBank(Authentication authentication) {
        return Response.ofSucceeded(service.getBankOfUser(JwtService.getUserName(authentication)));
    }

    @PutMapping("/user")
    public Response<UserResponse> update(Authentication authentication, @Valid @RequestBody UpdateUserRequest request) {
        return Response.ofSucceeded(service.updateUser(request, JwtService.getUserName(authentication)));
    }

    @GetMapping("/users")
    public Response<List<UserResponse>> getAllUser() {
        return Response.ofSucceeded(repository.findAll().stream().map(Entity2UserResponse.INSTANCE::map).toList());
    }

    @GetMapping("/current-user")
    public Response<UserResponse> getCurrentUser(Authentication authentication) {
        return Response.ofSucceeded(Entity2UserResponse.INSTANCE.map(service.getUserByPhone(JwtService.getUserName(authentication))));
    }

    @GetMapping("/admin/banks")
    @Operation(description = "Lấy tất cả tài khoản ngân hàng của admin")
    public Response<List<UserBankResponse>> getAdminBank() {
        return Response.ofSucceeded(service.getBankAdmin());
    }

    @PostMapping("/user/bank")
    @Operation(description = "Tạo mới tài khoản ngân hàng cho user")
    public Response<UserBankResponse> createBank(Authentication authentication, @RequestBody UserBankRequest userBankRequest) {
        return Response.ofSucceeded(service.createUserBank(userBankRequest, JwtService.getUserName(authentication)));
    }

    @PutMapping("/user/bank/{userBankId}")
    @Operation(description = "Cập nhật tài khoản ngân hàng cho user")
    public Response<UserBankResponse> updateBank(Authentication authentication, @RequestBody UserBankRequest userBankRequest, @PathVariable Long userBankId) {
        return Response.ofSucceeded(service.updateUserBank(userBankId, userBankRequest, JwtService.getUserName(authentication)));
    }

    @DeleteMapping("/user/bank/{userBankId}")
    @Operation(description = "Xóa tài khoản ngân hàng cho user")
    public Response<Boolean> deleteBank(Authentication authentication, @PathVariable Long userBankId) {
        return Response.ofSucceeded(service.delete(userBankId, JwtService.getUserName(authentication)));
    }

    @GetMapping("/user/hierarchy")
    @Operation(description = "Lấy danh sách cấp F1, F2...F10 của user")
    public Response<Map<Integer, List<UserResponse>>> getUserHierarchy(Authentication authentication) {
        return Response.ofSucceeded(service.getUserUserHierarchy(JwtService.getUserName(authentication)));
    }

    @GetMapping("/user/parent-hierarchy")
    @Operation(description = "Lấy danh sách cấp F1, F2...F10 của user")
    public Response<Map<Integer, UserResponse>> getParentHierarchy(Authentication authentication) {
        return Response.ofSucceeded(service.getParentHierarchy(JwtService.getUserName(authentication)));
    }

    @GetMapping("/user/my-team")
    @Operation(description = "Lấy thông tin đội nhóm của user")
    public Response<TeamResponse> getTeamInfo(Authentication authentication) {
        return Response.ofSucceeded(service.getLeaderTeam(JwtService.getUserName(authentication)));
    }

    @PutMapping("/user/{userId}/update-status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Lấy thông tin đội nhóm của user")
    public Response<UserResponse> updateStatusUser(@RequestBody UserUpdateStatusRequest request, @PathVariable Long userId) {
        var user = service.getUserById(userId);
        user.setStatus(Enum.valueOf(UserStatus.class, request.getStatus()));
        return Response.ofSucceeded(Entity2UserResponse.INSTANCE.map(repository.save(user)));
    }
}
