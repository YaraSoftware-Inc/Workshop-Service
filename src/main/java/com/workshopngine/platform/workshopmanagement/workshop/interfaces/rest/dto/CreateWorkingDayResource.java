package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

import java.time.LocalTime;

public record CreateWorkingDayResource(
        String day,
        LocalTime openTime,
        LocalTime closeTime
) {
}
