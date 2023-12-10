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
    @Schema(example = "FT1234567890", description = "Nội dung chuyển tiền, FE tự sinh")
    @NotBlank
    String description;
    @Schema(example = "MB BANK", description = "Tên ngân hàng nhận tiền của admin")
    @NotBlank
    String bank;
    @Schema(example = "9999999060699", description = "Số tài khoản chuyển tiền/nhận tiền của user")
    @NotBlank
    String numberAccount;
    @Schema(example = "NGUYEN VAN VY", description = "Tên tài khoản của user")
    @NotBlank
    String accountName;
    @Schema(example = "10000", description = "Số tiền giao dịch")
    Double amount;
    @Schema(example = "DEPOSIT", allowableValues = {"DEPOSIT", "WITHDRAW"}, description = "DEPOSIT: nạp tiền, WITHDRAW: rút tiền")
    @NotBlank(message = "Transaction type type is mandatory")
    TransactionType transactionType;
    @Schema(example = "PENDING", allowableValues = {"PENDING", "APPROVE", "CANCEL"}, description = "Trạng thái của giao dịch, mặc định là PENDING")
    @JsonIgnore
    TransactionStatus status = TransactionStatus.PENDING;
}
