package com.workshopngine.platform.workshopmanagement.workshop.domain.services;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.SeedDaysCommand;

public interface DayCommandService {
    void handle(SeedDaysCommand command);
}
