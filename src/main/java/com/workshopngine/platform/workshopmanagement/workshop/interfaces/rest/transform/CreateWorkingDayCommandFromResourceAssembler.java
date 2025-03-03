package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkingDayCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.CreateWorkingDayResource;

public class CreateWorkingDayCommandFromResourceAssembler {
    public static CreateWorkingDayCommand toCommandFromResource(String workshopId, CreateWorkingDayResource resource) {
        return new CreateWorkingDayCommand(
            workshopId,
            EDay.fromString(resource.day()),
            resource.openTime(),
            resource.closeTime()
        );
    }
}
