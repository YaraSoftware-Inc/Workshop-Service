package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record WorkingDayResource(
        Long id,
        String day,
        LocalTime openTime,
        LocalTime closeTime
) {
}
