package com.vn.investion.model.level;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GiftLevel {
    @Schema(description = "Phần trăm đạt được của level hiện tại")
    double progress;
    @Schema(description = "Mốc đầu tư cần đạt để đạt phần trăm level")
    Long total;
    @Schema(description = "Phần thưởng/lương sẽ nhận được")
    Long gift;

   public static GiftLevel of(double progress, Long total, Long gift){
       return new GiftLevel(progress, total, gift);
   }
}
