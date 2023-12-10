package com.vn.investion.utils;

import com.vn.investion.model.define.InvestType;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {
    public static long getCountInterest(InvestType type, OffsetDateTime interestDate, OffsetDateTime lastDate){
        Duration duration = Duration.between(interestDate, lastDate);
        switch (type){
            case HOURLY -> {
                return duration.toHours();
            }
            case DAILY -> {
               return duration.toDays();
            }
            case WEAKLY -> {
                return duration.toDays()/7;
            }
            case MONTHLY -> {
                return ChronoUnit.MONTHS.between(interestDate, OffsetDateTime.now());
            }
            case ANNUAL -> {
                return ChronoUnit.YEARS.between(interestDate, OffsetDateTime.now());
            }
            default -> {
                return 0;
            }
        }
    }

    public static OffsetDateTime getDateFromInvest(InvestType type, Integer duration){
        var now = OffsetDateTime.now();
        switch (type){
            case HOURLY -> {
                return now.plusHours(duration);
            }
            case DAILY -> {
                return now.plusDays(duration);
            }
            case WEAKLY -> {
                return now.plusWeeks(duration);
            }
            case MONTHLY -> {
                return now.plusMonths(duration);
            }
            case ANNUAL -> {
                return now.plusYears(duration);
            }
            default -> {
                return now;
            }
        }
    }
}
