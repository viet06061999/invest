package com.vn.investion.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDashboard {
    private UserSummary userSummary;
    private TransactionSummary transactionSummary;
}
