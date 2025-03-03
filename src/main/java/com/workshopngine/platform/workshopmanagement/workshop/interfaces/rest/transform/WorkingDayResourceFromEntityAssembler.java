package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkingDayResource;

public class WorkingDayResourceFromEntityAssembler {
    public static WorkingDayResource toResourceFromEntity(WorkingDay entity) {
        return new WorkingDayResource(
            entity.getId(),
            entity.getDay().toString(),
            entity.getOpenTime(),
            entity.getCloseTime()
        );
    }
}
