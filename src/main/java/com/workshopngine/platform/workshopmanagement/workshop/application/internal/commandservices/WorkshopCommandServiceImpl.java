package com.workshopngine.platform.workshopmanagement.workshop.application.internal.commandservices;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkingDayCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopCommandService;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkshopCommandServiceImpl implements WorkshopCommandService {
    private final WorkshopRepository workshopRepository;

    public WorkshopCommandServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public Long handle(CreateWorkshopCommand command) {
        var newWorkshop = new Workshop(command);
        try {
            workshopRepository.save(newWorkshop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving workshop: " + e.getMessage());
        }
        return newWorkshop.getId();
    }

    @Override
    public Long handle(UpdateWorkshopCommand command) {
        var workshop = workshopRepository.findById(command.workshopId()).orElseThrow();
        try {
            workshop.update(command);
            workshopRepository.save(workshop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating workshop: " + e.getMessage());
        }
        return workshop.getId();
    }

    @Override
    public Optional<WorkingDay> handle(CreateWorkingDayCommand command) {
        var workshop = workshopRepository.findById(command.workshopId());
        if (workshop.isEmpty()) throw new IllegalArgumentException("Workshop with ID %s not found".formatted(command.workshopId()));
        var newWorkingDay = workshop.get().getWorkingSchedule().addWorkingDay(workshop.get(), command);
        try {
            workshopRepository.save(workshop.get());
            return Optional.of(newWorkingDay);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving working day: " + e.getMessage());
        }
    }
}
