package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.auth.NotificationRequest;
import com.vn.investion.dto.auth.NotificationResponse;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.Entity2NotificationResponse;
import com.vn.investion.mapper.NotificationRequest2Entity;
import com.vn.investion.repo.NotificationRepository;
import com.vn.investion.repo.UserRepository;
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
public class NotificationController {
    private final NotificationRepository repository;
    private final UserRepository userRepository;

    @GetMapping("/notifications")
    @Operation(description = "Lấy tất cả notification của user hiện tại")
    public Response<List<NotificationResponse>> getAllReport(Authentication authentication) {
        var user = userRepository.findByPhone(JwtService.getUserName(authentication));
        return Response.ofSucceeded(repository.findAllByUserId(user.get().getId())
                .stream().map(Entity2NotificationResponse.INSTANCE::map).toList());
    }

    @PostMapping("/notification")
    @Operation(description = "Tạo mới notification")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<NotificationResponse> createNoti(@RequestBody NotificationRequest request) {
        var entity = NotificationRequest2Entity.INSTANCE.map(request);
        if(request.getUserId() != null){
            var user = userRepository.findById(request.getUserId());
            user.ifPresent(entity::setUser);
        }
        return Response.ofSucceeded(Entity2NotificationResponse.INSTANCE.map(repository.save(entity)));
    }

    @PutMapping("/notification/{notificationId}")
    @Operation(description = "Cập nhật notification")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<NotificationResponse> updateNoti(@RequestBody NotificationRequest request, @PathVariable Long notificationId) {
        var entityOptional = repository.findById(notificationId);
        var entity = entityOptional.get();
        NotificationRequest2Entity.INSTANCE.mapTo(request, entity);
        if(request.getUserId() != null){
            var user = userRepository.findById(request.getUserId());
            user.ifPresent(entity::setUser);
        }
        return Response.ofSucceeded(Entity2NotificationResponse.INSTANCE.map(repository.save(entity)));
    }

    @DeleteMapping("/notification/{notificationId}")
    @Operation(description = "Xóa tài khoản ngân hàng cho user")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<Boolean> deleteNoti( @PathVariable Long notificationId) {
        var entityOptional = repository.findById(notificationId);
        if(entityOptional.isEmpty()){
            throw new BusinessException(4004, "Notification not found", 404);
        }
        repository.delete(entityOptional.get());
        return Response.ofSucceeded(true);
    }
}
