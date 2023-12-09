package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.dto.transaction.TransactionRequest;
import com.vn.investion.dto.transaction.TransactionResponse;
import com.vn.investion.dto.transaction.TransactionUpdateStatusRequest;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.service.TransactionService;
import com.vn.investion.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction")
    public Response<TransactionResponse> createTransaction(
            Authentication authentication,
            @RequestBody TransactionRequest request) {
        var phone = JwtService.getUserName(authentication);
        return Response.ofSucceeded(transactionService.createTransaction(request, phone));
    }

    @PutMapping("/transaction/{transactionId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<TransactionResponse> approveTransaction(@PathVariable Long transactionId) {
        return Response.ofSucceeded(transactionService.updateStatusTransaction(
                new TransactionUpdateStatusRequest(TransactionStatus.APPROVE.name()),
                transactionId));
    }

    @PutMapping("/transaction/{transactionId}/cancel")
    public Response<TransactionResponse> cancelTransaction(@PathVariable Long transactionId) {
        return Response.ofSucceeded(transactionService.updateStatusTransaction(
                new TransactionUpdateStatusRequest(TransactionStatus.CANCEL.name()),
                transactionId));
    }

    @PutMapping("/transaction/{transactionId}")
    public Response<TransactionResponse> updateTransaction(
            @RequestBody TransactionRequest request,
            @PathVariable Long transactionId) {
        return Response.ofSucceeded(transactionService.updateTransaction(request, transactionId));
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<List<TransactionResponse>> getAll() {
        return Response.ofSucceeded(transactionService.getAll());
    }

    @GetMapping("/user/transactions")
    @Operation(description = "Lấy transaction của user hiện tại")
    public Response<List<TransactionResponse>> getByUser(Authentication authentication) {
        return Response.ofSucceeded(transactionService.getByUser(JwtService.getUserName(authentication)));
    }
}
