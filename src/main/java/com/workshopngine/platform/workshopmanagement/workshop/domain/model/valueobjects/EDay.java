package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

public enum EDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static EDay fromString(String day) {
        return switch (day) {
            case "MONDAY" -> MONDAY;
            case "TUESDAY" -> TUESDAY;
            case "WEDNESDAY" -> WEDNESDAY;
            case "THURSDAY" -> THURSDAY;
            case "FRIDAY" -> FRIDAY;
            case "SATURDAY" -> SATURDAY;
            case "SUNDAY" -> SUNDAY;
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
    }
}
