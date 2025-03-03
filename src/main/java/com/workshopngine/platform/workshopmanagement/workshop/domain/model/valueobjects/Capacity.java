package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Capacity(Integer maxCapacityVehicle, Integer maxCapacityMechanic) {
    public Capacity() { this(null, null); }

    public Capacity {
        if (maxCapacityVehicle == null || maxCapacityVehicle <= 0) {
            throw new IllegalArgumentException("Max capacity vehicle must be greater than 0");
        }
        if (maxCapacityMechanic == null || maxCapacityMechanic <= 0) {
            throw new IllegalArgumentException("Max capacity employee must be greater than 0");
        }
    }
}
