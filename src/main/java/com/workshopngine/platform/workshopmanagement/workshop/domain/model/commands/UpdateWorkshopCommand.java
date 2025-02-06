package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.*;

public record UpdateWorkshopCommand(
        String workshopId,
        WorkshopInfo information,
        Location location,
        Capacity capacity
) {
}
