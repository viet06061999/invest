package com.vn.investion.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReportRequest {
    @NotBlank
    @Schema(description = "Tiêu đề", example = "Lỗi")
    private String title;

    @NotBlank
    @Schema(description = "Nội dung báo cáo", example = "Chưa nhận được tiền")
    private String report;

    @Schema(description = "File đính kèm", example = "abc.jpg")
    private String attach;
}
