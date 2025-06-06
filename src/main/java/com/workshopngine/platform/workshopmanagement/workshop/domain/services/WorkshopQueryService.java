package com.workshopngine.platform.workshopmanagement.workshop.domain.services;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetAllWorkingDaysByWorkshopIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByOwnerIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.IsAvailableWorkshopByIdAndRequestedTimeQuery;

import java.util.Collection;
import java.util.Optional;

public interface WorkshopQueryService {
    Optional<Workshop> handle(GetWorkshopByOwnerIdQuery query);
    Optional<Workshop> handle(GetWorkshopByIdQuery query);
    Collection<WorkingDay> handle(GetAllWorkingDaysByWorkshopIdQuery query);
    Boolean handle(IsAvailableWorkshopByIdAndRequestedTimeQuery query);
}
