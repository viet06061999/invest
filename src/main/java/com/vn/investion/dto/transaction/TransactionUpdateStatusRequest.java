package com.vn.investion.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionUpdateStatusRequest {
    @Schema(example = "APPROVE", allowableValues = {"APPROVE", "CANCEL"})
    String status;
}
