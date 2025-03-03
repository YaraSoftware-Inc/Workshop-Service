package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record WorkshopInfo(String name, String description) {
    public WorkshopInfo() {
        this(null, null);
    }

    public WorkshopInfo {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Workshop name cannot be null or blank");
        }
    }
}
