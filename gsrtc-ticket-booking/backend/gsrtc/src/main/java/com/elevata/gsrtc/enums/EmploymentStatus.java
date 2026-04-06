package com.elevata.gsrtc.enums;

public enum EmploymentStatus {
    ACTIVE,
    INACTIVE,
    ON_LEAVE,
    RESIGNED,
    SUSPENDED;

    public static EmploymentStatus getEmploymentStatus(String status) {
        return switch (status.toUpperCase()) {
            case "ACTIVE" -> EmploymentStatus.ACTIVE;
            case "ON_LEAVE" -> EmploymentStatus.ON_LEAVE;
            case "SUSPEND" -> EmploymentStatus.SUSPENDED;
            case "RESIGNED" -> EmploymentStatus.RESIGNED;
            default -> EmploymentStatus.INACTIVE;
        };
    }
}
