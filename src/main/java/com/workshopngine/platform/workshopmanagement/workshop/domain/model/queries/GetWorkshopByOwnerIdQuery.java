package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

public record GetWorkshopByOwnerIdQuery(Long ownerId) {
    public GetWorkshopByOwnerIdQuery {
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException("Owner id must be a positive number");
        }
    }
}
