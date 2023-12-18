package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.auth.ReportRequest;
import com.vn.investion.dto.auth.ReportResponse;
import com.vn.investion.dto.auth.UserUpdateStatusRequest;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.Entity2ReportResponse;
import com.vn.investion.mapper.ReportRequest2Entity;
import com.vn.investion.model.define.ReportStatus;
import com.vn.investion.repo.ReportRepository;
import com.vn.investion.service.UserService;
import com.vn.investion.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReportController {
    private final ReportRepository repository;
    private final UserService userService;

    @GetMapping("/reports")
    @Operation(description = "Lấy tất cả report hiện tại")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<List<ReportResponse>> getAllReport() {
        return Response.ofSucceeded(repository.findAll().stream().map(Entity2ReportResponse.INSTANCE::map).toList());
    }

    @PostMapping("/report")
    @Operation(description = "Tạo mới report")
    public Response<ReportResponse> createReport(Authentication authentication, @RequestBody ReportRequest request) {
        var user = userService.getUserByPhone(JwtService.getUserName(authentication));
        var entity =ReportRequest2Entity.INSTANCE.map(request);
        entity.setUser(user);
        return Response.ofSucceeded(Entity2ReportResponse.INSTANCE.map(repository.save(entity)));
    }

    @PutMapping("/report/{reportId}")
    @Operation(description = "Cập nhật report")
    public Response<ReportResponse> updateReport(Authentication authentication, @RequestBody ReportRequest request, @PathVariable Long reportId) {
        var user = userService.getUserByPhone(JwtService.getUserName(authentication));
        var entityOptional = repository.findById(reportId);
        if(entityOptional.isEmpty() || !entityOptional.get().getUser().getPhone().equals(user.getPhone())){
            throw new BusinessException(4003, "not permission", 403);
        }
        var entity = entityOptional.get();
        ReportRequest2Entity.INSTANCE.mapTo(request, entity);
        return Response.ofSucceeded(Entity2ReportResponse.INSTANCE.map(repository.save(entity)));
    }

    @PutMapping("/report/{reportId}/update-status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Lấy thông tin đội nhóm của user")
    public Response<ReportResponse> updateStatusUser(@RequestBody UserUpdateStatusRequest request, @PathVariable Long reportId) {
        var entityOptional = repository.findById(reportId);
        if(entityOptional.isEmpty()){
            throw new BusinessException(4004, "Not found", 404);
        }
        entityOptional.get().setStatus(Enum.valueOf(ReportStatus.class, request.getStatus()));
        return Response.ofSucceeded(Entity2ReportResponse.INSTANCE.map(repository.save(entityOptional.get())));
    }

    @DeleteMapping("/report/{reportId}")
    @Operation(description = "Xóa tài khoản ngân hàng cho user")
    public Response<Boolean> deleteBank(Authentication authentication, @PathVariable Long reportId) {
        var user = userService.getUserByPhone(JwtService.getUserName(authentication));
        var entityOptional = repository.findById(reportId);
        if(entityOptional.isEmpty() || !entityOptional.get().getUser().getPhone().equals(user.getPhone())){
            throw new BusinessException(4003, "not permission", 403);
        }
        repository.delete(entityOptional.get());
        return Response.ofSucceeded(true);
    }
}
