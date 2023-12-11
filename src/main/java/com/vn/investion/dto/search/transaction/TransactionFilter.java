package com.vn.investion.dto.search.transaction;

import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFilter {
    private int page;
    private int perPage;
    @Schema(description = "loại giao dịch")
    private TransactionType type;
    private TransactionStatus status;
    private String updatedFrom;
    private String updatedTo;
}
