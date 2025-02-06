package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

import java.time.LocalTime;

public record WorkingDayResource(
        String id,
        String day,
        LocalTime openTime,
        LocalTime closeTime
) {
}
