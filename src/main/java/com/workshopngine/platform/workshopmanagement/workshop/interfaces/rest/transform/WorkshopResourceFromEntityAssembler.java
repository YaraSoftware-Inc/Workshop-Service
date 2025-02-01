package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkshopResource;

public class WorkshopResourceFromEntityAssembler {
    public static WorkshopResource toResourceFromEntity(Workshop workshop) {
        return new WorkshopResource(
                workshop.getId(),
                workshop.getInformation().name(),
                workshop.getInformation().description(),
                workshop.getLocation().address(),
                workshop.getLocation().street(),
                workshop.getLocation().city(),
                workshop.getLocation().zipCode(),
                workshop.getLocation().country(),
                workshop.getCapacity().maxCapacityVehicle(),
                workshop.getCapacity().maxCapacityMechanic(),
                workshop.getOwnerId().ownerId(),
                workshop.getStatus().toString()
        );
    }
}
