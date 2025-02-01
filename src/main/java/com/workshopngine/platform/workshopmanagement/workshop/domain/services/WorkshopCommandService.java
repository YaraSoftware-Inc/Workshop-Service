package com.workshopngine.platform.workshopmanagement.workshop.domain.services;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopCommand;

public interface WorkshopCommandService {
    Long handle(CreateWorkshopCommand command);
    Long handle(UpdateWorkshopCommand command);
}
