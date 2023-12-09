package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.ipackage.InvestPackageRequest;
import com.vn.investion.dto.ipackage.InvestPackageResponse;
import com.vn.investion.dto.ipackage.LeaderPackageRequest;
import com.vn.investion.dto.ipackage.LeaderPackageResponse;
import com.vn.investion.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class PackageController {
    private final PackageService packageService;

    @GetMapping("/invests")
    public Response<List<InvestPackageResponse>> getAllInvest() {
        return Response.ofSucceeded(packageService.getAllInvest());
    }

    @GetMapping("/leaders")
    public Response<List<LeaderPackageResponse>> getAllLeader() {
        return Response.ofSucceeded(packageService.getAllLeader());
    }

    @GetMapping("/leader/{leaderPackageId}")
    public Response<LeaderPackageResponse> getDetailLeader(@PathVariable("leaderPackageId") Long packageId) {
        return Response.ofSucceeded(packageService.getDetailLeader(packageId));
    }

    @GetMapping("/invest/{investPackageId}")
    public Response<InvestPackageResponse> getDetailInvest(@PathVariable("investPackageId") Long packageId) {
        return Response.ofSucceeded(packageService.getDetailInvest(packageId));
    }

    @PostMapping("/invest")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<InvestPackageResponse> createInvest(@Valid @RequestBody(required = true) InvestPackageRequest request) {
        return Response.ofSucceeded(packageService.createInvestPackage(request));
    }

    @PostMapping("/leader")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<LeaderPackageResponse> createLeader(@Valid @RequestBody(required = true) LeaderPackageRequest request) {
        return Response.ofSucceeded(packageService.createLeaderPackage(request));
    }

    @PutMapping("/leader/{leaderPackageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<LeaderPackageResponse> updateLeader(@Valid @RequestBody(required = true) LeaderPackageRequest request,
                                                        @PathVariable Long leaderPackageId) {
        return Response.ofSucceeded(packageService.updateLeaderPackage(request, leaderPackageId));
    }

    @PutMapping("/invest/{investPackageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<InvestPackageResponse> updateInvest(@Valid @RequestBody(required = true) InvestPackageRequest request,
                                                        @PathVariable Long investPackageId) {
        return Response.ofSucceeded(packageService.updateInvestPackage(request, investPackageId));
    }

    @DeleteMapping("/invest/{investPackageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<Boolean> deleteInvest(@PathVariable Long investPackageId) {
        return Response.ofSucceeded(packageService.deleteInvest(investPackageId));
    }

    @DeleteMapping("/leader/{leaderPackageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<Boolean> deleteLeader(@PathVariable Long leaderPackageId) {
        return Response.ofSucceeded(packageService.deleteLeader(leaderPackageId));
    }
}
