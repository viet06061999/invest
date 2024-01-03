package com.vn.investion.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionSummary {
    @Schema(description = "Tổng số tiền nạp")
    BigDecimal totalDeposit;
    @Schema(description = "Tổng số tiền rút")
    BigDecimal totalCredit;
}
