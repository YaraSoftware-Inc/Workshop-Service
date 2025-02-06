package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;

import java.time.LocalTime;

public record CreateWorkingDayCommand(
        String workshopId,
        EDay day,
        LocalTime openTime,
        LocalTime closeTime
) {
}
