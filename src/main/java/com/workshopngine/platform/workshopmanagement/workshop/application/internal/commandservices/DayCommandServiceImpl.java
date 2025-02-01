package com.workshopngine.platform.workshopmanagement.workshop.application.internal.commandservices;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.SeedDaysCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.Day;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.DayCommandService;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.DayRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DayCommandServiceImpl implements DayCommandService {
    private final DayRepository dayRepository;

    public DayCommandServiceImpl(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public void handle(SeedDaysCommand command) {
        Arrays.stream(EDay.values()).forEach(day -> {
            if (!dayRepository.existsByName(day)) {
                dayRepository.save(new Day(EDay.valueOf(day.name())));
            }
        });
    }
}
