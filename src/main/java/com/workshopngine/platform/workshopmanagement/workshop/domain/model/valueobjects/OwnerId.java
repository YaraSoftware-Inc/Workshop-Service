package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OwnerId(Long ownerId) {
    public OwnerId() {this(null);}
    public OwnerId {
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException("Owner ID cannot be null or less than or equal to 0");
        }
    }
}
