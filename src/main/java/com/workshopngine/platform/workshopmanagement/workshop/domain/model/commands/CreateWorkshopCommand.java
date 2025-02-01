package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.*;

public record CreateWorkshopCommand(
        WorkshopInfo information,
        Location location,
        Capacity capacity,
        OwnerId ownerId)
{
    public CreateWorkshopCommand {
        if (information == null) {
            throw new IllegalArgumentException("Information must not be null");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location must not be null");
        }
        if (capacity == null) {
            throw new IllegalArgumentException("Capacity must not be null");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner id must not be null");
        }
    }
}
