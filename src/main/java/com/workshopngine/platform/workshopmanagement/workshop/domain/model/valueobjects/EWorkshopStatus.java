package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

public enum EWorkshopStatus {
    CLOSED,
    OPEN,
    FULL_CAPACITY,
    OUT_OF_SERVICE;

    public static EWorkshopStatus fromString(String status) {
        for (EWorkshopStatus s : EWorkshopStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
