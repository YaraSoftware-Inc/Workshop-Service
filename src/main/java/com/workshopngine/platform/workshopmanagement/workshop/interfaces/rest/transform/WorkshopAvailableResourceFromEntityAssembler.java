package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkshopAvailabilityResource;

public class WorkshopAvailableResourceFromEntityAssembler {
    public static WorkshopAvailabilityResource toResourceFromEntity(String workshopId, Boolean available) {
        return new WorkshopAvailabilityResource(workshopId, available);
    }
}
