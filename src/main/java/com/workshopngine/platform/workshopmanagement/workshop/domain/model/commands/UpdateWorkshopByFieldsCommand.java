package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EWorkshopStatus;

public record UpdateWorkshopByFieldsCommand(
        String workshopId,
        EWorkshopStatus status
) {
}
