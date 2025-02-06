package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.*;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.UpdateWorkshopResource;

public class UpdateWorkshopCommandFromResourceAssembler {
    public static UpdateWorkshopCommand toCommandFromResource(String workshopId, UpdateWorkshopResource resource) {
        return new UpdateWorkshopCommand(
                workshopId,
                new WorkshopInfo(resource.name(), resource.description()),
                new Location(resource.address(), resource.street(), resource.city(), resource.zipCode(), resource.country()),
                new Capacity(resource.maxCapacityVehicle(), resource.maxCapacityMechanic())
        );
    }
}
