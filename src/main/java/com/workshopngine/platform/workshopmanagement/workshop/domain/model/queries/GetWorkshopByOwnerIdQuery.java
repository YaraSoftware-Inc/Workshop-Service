package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

public record GetWorkshopByOwnerIdQuery(String ownerId) {
    public GetWorkshopByOwnerIdQuery {
        if (ownerId == null || ownerId.isBlank()) {
            throw new IllegalArgumentException("Owner id cannot be null or empty");
        }
    }
}
