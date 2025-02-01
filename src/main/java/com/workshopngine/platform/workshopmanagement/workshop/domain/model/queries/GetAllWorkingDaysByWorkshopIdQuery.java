package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

public record GetAllWorkingDaysByWorkshopIdQuery(Long workshopId) {
    public GetAllWorkingDaysByWorkshopIdQuery {
        if (workshopId == null || workshopId <= 0) {
            throw new IllegalArgumentException("WorkshopId must be a positive number");
        }
    }
}
