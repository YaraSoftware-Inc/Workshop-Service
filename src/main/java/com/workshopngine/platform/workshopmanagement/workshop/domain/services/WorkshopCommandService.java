package com.workshopngine.platform.workshopmanagement.workshop.domain.services;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkingDayCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopByFieldsCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;

import java.util.Optional;

public interface WorkshopCommandService {
    Long handle(CreateWorkshopCommand command);
    Long handle(UpdateWorkshopCommand command);
    Long handle(UpdateWorkshopByFieldsCommand command);
    Optional<WorkingDay> handle(CreateWorkingDayCommand command);
}
