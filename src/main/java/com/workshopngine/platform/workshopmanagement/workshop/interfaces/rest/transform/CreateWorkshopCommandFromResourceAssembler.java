package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.*;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.CreateWorkshopResource;

public class CreateWorkshopCommandFromResourceAssembler {
    public static CreateWorkshopCommand toCommandFromResource(CreateWorkshopResource resource) {
        return new CreateWorkshopCommand(
                new WorkshopInfo(resource.name(), resource.description()),
                new Location(resource.address(), resource.street(), resource.city(), resource.zipCode(), resource.country()),
                new Capacity(resource.maxCapacityVehicle(), resource.maxCapacityMechanic()),
                new WorkingSchedule(resource.workingDays(), resource.openTime(), resource.closeTime()),
                new OwnerId(resource.ownerId())
        );
    }
}
