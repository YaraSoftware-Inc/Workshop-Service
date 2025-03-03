package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OwnerId(String ownerId) {
    public OwnerId() {this(null);}
    public OwnerId {
        if (ownerId == null || ownerId.isBlank()) {
            throw new IllegalArgumentException("Owner id cannot be null or empty");
        }
    }
}
