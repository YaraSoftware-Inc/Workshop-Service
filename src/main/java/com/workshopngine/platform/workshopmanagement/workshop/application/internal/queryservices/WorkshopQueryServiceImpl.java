package com.workshopngine.platform.workshopmanagement.workshop.application.internal.queryservices;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetAllWorkingDaysByWorkshopIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByOwnerIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.IsAvailableWorkshopByIdAndRequestedTimeQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.OwnerId;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopQueryService;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class WorkshopQueryServiceImpl implements WorkshopQueryService {
    private final WorkshopRepository workshopRepository;

    public WorkshopQueryServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public Optional<Workshop> handle(GetWorkshopByOwnerIdQuery query) {
        var ownerId = new OwnerId(query.ownerId());
        return workshopRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Workshop> handle(GetWorkshopByIdQuery query) {
        return workshopRepository.findById(query.workshopId());
    }

    @Override
    public Collection<WorkingDay> handle(GetAllWorkingDaysByWorkshopIdQuery query) {
        var workshop = workshopRepository.findById(query.workshopId());
        if (workshop.isEmpty()) throw new IllegalArgumentException("Workshop with ID %s not found".formatted(query.workshopId()));
        return workshop.get().getWorkingSchedule().getWorkingDays();
    }

    @Override
    public Boolean handle(IsAvailableWorkshopByIdAndRequestedTimeQuery query) {
        var workshop = workshopRepository.findById(query.workshopId());
        if (workshop.isEmpty()) return false;
        return workshop.get().getWorkingSchedule().isAvailableAtRequestedTime(query.requestedTime());
    }
}
