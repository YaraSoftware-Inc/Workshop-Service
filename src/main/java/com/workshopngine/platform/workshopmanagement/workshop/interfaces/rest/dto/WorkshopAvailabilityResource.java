package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

public record WorkshopAvailabilityResource(
        String workshopId,
        Boolean available
) {
}
