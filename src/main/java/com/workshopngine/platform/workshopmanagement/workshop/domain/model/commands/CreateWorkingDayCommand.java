package com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;

import java.time.LocalTime;

public record CreateWorkingDayCommand(
        Long workshopId,
        EDay day,
        LocalTime openTime,
        LocalTime closeTime
) {
}
