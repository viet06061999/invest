package com.vn.investion.utils;

import com.vn.investion.model.level.ConditionLevel;
import com.vn.investion.model.level.GiftLevel;

import java.util.List;
import java.util.Map;

public class Commission {
    public static Map<Integer, Double> rate = Map.of(
            1, 0.05,
            2, 0.02,
            3, 0.01
            );

    public static Map<Integer, ConditionLevel> conditionLevel = Map.of(
            1, new ConditionLevel(200000000L, 10, 5),
            2, new ConditionLevel(1000000000L, 50, 10),
            3, new ConditionLevel(4000000000L, 100, 20),
            4, new ConditionLevel(10000000000L, 200, 50),
            5, new ConditionLevel(20000000000L, 500, 50),
            6, new ConditionLevel(50000000000L, 1000, 50),
            7, new ConditionLevel(200000000000L, 3000, 50)
    );

    public static Map<Integer, List<GiftLevel>> giftLevel = Map.of(
            1, List.of( GiftLevel.of(0.5, 100000000L, 500000L),GiftLevel.of(0.6, 120000000L, 600000L),GiftLevel.of(0.7, 140000000L, 700000L),GiftLevel.of(0.8, 160000000L, 800000L),GiftLevel.of(0.9, 180000000L, 900000L),GiftLevel.of(1, 200000000L, 1000000L)),
            2, List.of( GiftLevel.of(0.5, 500000000L, 1500000L),GiftLevel.of(0.6, 600000000L, 1800000L),GiftLevel.of(0.7, 700000000L, 2100000L),GiftLevel.of(0.8, 800000000L, 2400000L),GiftLevel.of(0.9, 900000000L, 2700000L),GiftLevel.of(1, 1000000000L, 3000000L)),
            3, List.of( GiftLevel.of(0.5, 2000000000L, 4000000L),GiftLevel.of(0.6, 2400000000L, 4800000L),GiftLevel.of(0.7, 2800000000L, 5600000L),GiftLevel.of(0.8, 3200000000L, 6400000L),GiftLevel.of(0.9, 3600000000L, 7200000L),GiftLevel.of(1, 4000000000L, 8000000L)),
            4, List.of( GiftLevel.of(0.5, 5000000000L, 25000000L),GiftLevel.of(0.6, 6000000000L, 30000000L),GiftLevel.of(0.7, 7000000000L, 35000000L),GiftLevel.of(0.8, 8000000000L, 40000000L),GiftLevel.of(0.9, 9000000000L, 45000000L),GiftLevel.of(1, 10000000000L, 50000000L)),
            5, List.of( GiftLevel.of(0.5, 10000000000L, 50000000L),GiftLevel.of(0.6, 12000000000L, 60000000L),GiftLevel.of(0.7, 14000000000L, 70000000L),GiftLevel.of(0.8, 16000000000L, 80000000L),GiftLevel.of(0.9, 18000000000L, 90000000L),GiftLevel.of(1, 200000000000L, 100000000L)),
            6, List.of( GiftLevel.of(0.5, 25000000000L, 100000000L),GiftLevel.of(0.6, 30000000000L, 120000000L),GiftLevel.of(0.7, 35000000000L, 140000000L),GiftLevel.of(0.8, 40000000000L, 160000000L),GiftLevel.of(0.9, 45000000000L, 180000000L),GiftLevel.of(1, 500000000000L, 200000000L)),
            7, List.of( GiftLevel.of(0.5, 100000000000L, 250000000L),GiftLevel.of(0.6, 120000000000L, 300000000L),GiftLevel.of(0.7, 140000000000L, 350000000L),GiftLevel.of(0.8, 160000000000L, 400000000L),GiftLevel.of(0.9, 180000000000L, 450000000L),GiftLevel.of(1, 2000000000000L, 5000000000L))
    );

    public static int getLevel(Long totalDeposit, long totalMember, long totalMemberF1) {
        if(totalDeposit == null){
            return 0;
        }
        int maxLevel = 0;

        for (Map.Entry<Integer, ConditionLevel> entry : conditionLevel.entrySet()) {
            ConditionLevel condition = entry.getValue();
            if (totalDeposit >= condition.getTotalDeposit()
                    && totalMember >= condition.getTotalMember()
                    && totalMemberF1 >= condition.getTotalF1()) {
                maxLevel = Math.max(maxLevel, entry.getKey());
            }
        }
        return maxLevel; // Trả về 0 nếu không thỏa mãn điều kiện cho bất kỳ level nào
    }

    public static GiftLevel getGiftLevel(Long totalDeposit, long totalMember, long totalMemberF1){
        var level = getLevel(totalDeposit, totalMember, totalMemberF1);
        if(level <= 0 || level > 7){
            return null;
        }
        GiftLevel gift = null;
        var giftLevelList = giftLevel.get(level);
        for(GiftLevel giftLevel: giftLevelList){
            if(giftLevel.getTotal() <= totalDeposit){
                gift = giftLevel;
            }
        }
        return gift;
    }
}
