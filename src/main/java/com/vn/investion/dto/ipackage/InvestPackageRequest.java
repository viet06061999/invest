package com.vn.investion.dto.ipackage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class InvestPackageRequest {
    @Schema(example = "Đầu tư thông minh", description = "tiêu đề")
    String title;
    @Schema(example = "Đầu tư thông minh", description = "Nội dung")
    String description;
    @Schema(example = "3", description = "Thời hạn đầu tư với đơn vị là investType")
    Integer duration;
    @Schema(example = "DAILY", allowableValues = {"HOURLY", "DAILY", "WEEKLY", "MONTHLY", "ANNUAL"}, description = "Đơn vị đầu tư tương ứng giờ, ngày, tuần, tháng, năm")
    @NotBlank(message = "Invest type is mandatory")
    String investType;
    @PositiveOrZero
    @Schema(example = "10000", description = "Số tiền vốn để đầu tư")
    Double amt;
    @PositiveOrZero
    @Schema(example = "0.1", description = "Lãi suất đầu tư tính theo đơn vị investType, ví dụ này là 10% 1 ngày")
    Double rate;
}
