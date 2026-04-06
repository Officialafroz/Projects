package com.elevata.gsrtc.service;

import org.springframework.stereotype.Service;

@Service
public class ReferenceNumberService {
    public String generatePassengerRef(String pnr, int passengerNo) {
        return pnr + "-P" + passengerNo;
    }

    public static String generateTransRef() {
        return "TXN" + System.currentTimeMillis();
    }

    public static String generateStf(String stf) {
        int next = Integer.parseInt(stf.substring(3)) + 1;
        return "stf" + String.format("%05d", next);
    }

    public static String generatePNR() {
        String prefix = "GS";

        // last 6 digits of timestamp
        String timePart = String.valueOf(System.currentTimeMillis());
        timePart = timePart.substring(timePart.length() - 6);

        // 2-digit random number (10–99)
        int random = 10 + (int)(Math.random() * 90);

        return prefix + timePart + random;
    }
}
