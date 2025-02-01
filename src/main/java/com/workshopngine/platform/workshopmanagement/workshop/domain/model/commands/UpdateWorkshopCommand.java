package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.*;

public record UpdateWorkshopCommand(
        Long workshopId,
        WorkshopInfo information,
        Location location,
        Capacity capacity
) {
}
