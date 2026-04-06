package com.elevata.gsrtc.util;

import java.util.Objects;

public final class StringNormalizer {
    public static String normalize(String value) {

        //        return Objects.equals(value, "") || value.isBlank() ? null : value;
        return value == null || value.trim().isEmpty() ? null : value;
    }
}
