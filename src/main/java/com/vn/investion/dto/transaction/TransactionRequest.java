package com.vn.investion.dto.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TransactionRequest{
    @Schema(example = "FT1234567890")
    @NotBlank
    String description;
    @Schema(example = "MB BANK")
    @NotBlank
    String bank;
    @Schema(example = "9999999060699")
    @NotBlank
    String numberAccount;
    @Schema(example = "NGUYEN VAN VY")
    @NotBlank
    String accountName;
    @Schema(example = "10000")
    Double amount;
    @Schema(example = "DEPOSIT", allowableValues = {"DEPOSIT", "WITHDRAW"})
    @NotBlank(message = "Transaction type type is mandatory")
    TransactionType transactionType;
    @Schema(example = "PENDING", allowableValues = {"PENDING", "APPROVE", "CANCEL"})
    @JsonIgnore
    TransactionStatus status = TransactionStatus.PENDING;
}
