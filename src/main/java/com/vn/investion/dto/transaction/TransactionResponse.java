package com.vn.investion.dto.transaction;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
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
    Double remainPoint;
    String createdBy;
    String updatedBy;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
