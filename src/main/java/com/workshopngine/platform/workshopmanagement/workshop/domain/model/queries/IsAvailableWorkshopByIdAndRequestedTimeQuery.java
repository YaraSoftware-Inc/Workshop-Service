package com.workshopngine.platform.workshopmanagement.workshop.domain.model.queries;

import java.time.LocalDateTime;

public record IsAvailableWorkshopByIdAndRequestedTimeQuery(
    String workshopId,
    LocalDateTime requestedTime
) {
}
