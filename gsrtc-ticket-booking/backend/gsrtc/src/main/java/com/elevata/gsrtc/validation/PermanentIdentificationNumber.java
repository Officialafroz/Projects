package com.elevata.gsrtc.validation;

import jakarta.validation.Payload;

public @interface PermanentIdentificationNumber {
    String message() default "Invalid Vehicle number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
