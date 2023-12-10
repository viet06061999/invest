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
public class LeaderPackageRequest {
    @Schema(example = "Đầu tư thông minh")
    String title;
    @Schema(example = "Đầu tư thông minh")
    String description;
    @Schema(example = "3")
    Integer duration;
    @Schema(example = "DAILY", allowableValues = {"HOURLY", "DAILY", "WEAKLY", "MONTHLY", "ANNUAL"})
    @NotBlank(message = "Invest type is mandatory")
    String investType;
    @PositiveOrZero
    @Schema(example = "10000")
    Long amt;
    @PositiveOrZero
    @Schema(example = "0.1")
    Double rate;
}
