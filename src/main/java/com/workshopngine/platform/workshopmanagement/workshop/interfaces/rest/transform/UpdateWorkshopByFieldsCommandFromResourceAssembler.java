package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.transform;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopByFieldsCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EWorkshopStatus;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.UpdateWorkshopByFieldsResource;

public class UpdateWorkshopByFieldsCommandFromResourceAssembler {
    public static UpdateWorkshopByFieldsCommand toCommandFromResource(String workshopId, UpdateWorkshopByFieldsResource resource) {
        return new UpdateWorkshopByFieldsCommand(
                workshopId,
                EWorkshopStatus.fromString(resource.status())
        );
    }
}
