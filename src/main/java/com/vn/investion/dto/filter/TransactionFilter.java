package com.vn.investion.dto.filter;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionFilter {
    int size = 20;
    int page = 0;
    String transactionType;
    String status;
    Long amountFrom;
    Long amountTo;
    String updatedBy;
    String updatedAtFrom;
    String updatedAtTo;
}
