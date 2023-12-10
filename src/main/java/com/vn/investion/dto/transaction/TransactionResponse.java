package com.vn.investion.dto.transaction;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TransactionResponse {
    Integer id;
    TransactionType transactionType;
    TransactionStatus status;
    String numberAccount;
    String accountName;
    String bank;
    Double amount;
    String description;
    UserResponse user;
    @Schema(description = "Số dư sau giao dịch của user")
    Double remainBalance;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
