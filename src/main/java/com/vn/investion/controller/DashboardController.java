package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.dashboard.AdminDashboard;
import com.vn.investion.mapper.Entity2TransactionSummary;
import com.vn.investion.mapper.Entity2UserSummary;
import com.vn.investion.repo.TransactionHisRepository;
import com.vn.investion.repo.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
@AllArgsConstructor
public class DashboardController {
    private final UserRepository userRepository;
    private final TransactionHisRepository transactionHisRepository;

    @GetMapping("/dashboard/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<AdminDashboard> downloadImage() {
        var user = userRepository.getDashboardUser();
        var his = transactionHisRepository.getDashboardTransaction();
        var dashboard = new AdminDashboard(
                Entity2UserSummary.INSTANCE.map(user),
                Entity2TransactionSummary.INSTANCE.map(his)
        );
        return Response.ofSucceeded(dashboard);
    }
}
