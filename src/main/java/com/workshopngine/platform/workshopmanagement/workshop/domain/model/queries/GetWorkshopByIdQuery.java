package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

public record GetWorkshopByIdQuery(String workshopId) {
    public GetWorkshopByIdQuery {
        if (workshopId == null || workshopId.isBlank()) {
            throw new IllegalArgumentException("Workshop id cannot be null or empty");
        }
    }
}
