package com.workshopngine.platform.workshopmanagement.workshop.application.internal.queryservices;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries.GetWorkshopByOwnerIdQuery;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.OwnerId;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopQueryService;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.WorkshopRepository;
import org.springframework.stereotype.Service;

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
}
