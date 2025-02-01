package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EWorkshopStatus;

public record UpdateWorkshopByFieldsCommand(
        Long workshopId,
        EWorkshopStatus status
) {
}
