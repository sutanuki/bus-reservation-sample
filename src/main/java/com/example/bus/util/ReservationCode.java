package com.example.bus.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ReservationCode {
    private static final Random RAND = new Random();

    public static String generate() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        int rand = 1000 + RAND.nextInt(9000);
        return "B" + ts + rand;
    }
}