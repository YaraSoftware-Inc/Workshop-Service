package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

public record GetAllWorkingDaysByWorkshopIdQuery(String workshopId) {
    public GetAllWorkingDaysByWorkshopIdQuery {
        if (workshopId == null || workshopId.isBlank()) {
            throw new IllegalArgumentException("workshopId cannot be null or empty");
        }
    }
}
