package com.workshopngine.platform.workshopmanagement.workshop.application.internal.commandservices;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.Day;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopCommandService;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.DayRepository;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WorkshopCommandServiceImpl implements WorkshopCommandService {
    private final WorkshopRepository workshopRepository;
    private final DayRepository dayRepository;

    public WorkshopCommandServiceImpl(WorkshopRepository workshopRepository, DayRepository dayRepository) {
        this.workshopRepository = workshopRepository;
        this.dayRepository = dayRepository;
    }

    @Override
    public Long handle(CreateWorkshopCommand command) {
        var newWorkshop = new Workshop(command);
        var days = command.schedule().getWorkingDays();
        newWorkshop.getSchedule().setWorkingDays(getDays(days));
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
        var days = command.schedule().getWorkingDays();
        workshop.getSchedule().setWorkingDays(getDays(days));
        try {
            workshop.update(command);
            workshopRepository.save(workshop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating workshop: " + e.getMessage());
        }
        return workshop.getId();
    }

    private Collection<Day> getDays(Collection<Day> days) {
        return days.stream().map(day -> dayRepository.findByName(day.getName())
                .orElseThrow(() -> new RuntimeException("Day not found"))).toList();
    }
}
