package com.vn.investion.utils;

import java.util.UUID;

public class ReferralCodeGenerator {
    public static String generateReferralCode() {
        String referralCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        return referralCode;
    }
}