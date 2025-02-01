package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

public record GetWorkshopByIdQuery(Long workshopId) {
    public GetWorkshopByIdQuery {
        if (workshopId == null || workshopId <= 0) {
            throw new IllegalArgumentException("Workshop id must be a positive number");
        }
    }
}
