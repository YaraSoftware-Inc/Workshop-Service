package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

class WorkingScheduleTest {

    @Test
    void TestIsAvailableAtRequestedTime_NoWorkingDays_ShouldReturnFalse() {
        // Given
        WorkingSchedule workingSchedule = new WorkingSchedule();
        LocalDateTime requestedTime = LocalDateTime.now();
        // When
        Boolean isAvailable = workingSchedule.isAvailableAtRequestedTime(requestedTime);
        // Then
        Assertions.assertThat(isAvailable).isFalse();
    }

    @Test
    void TestIsAvailableAtRequestedTime_WorkingDayNotWithinRange_ShouldReturnFalse() {
        // Given
        WorkingSchedule workingSchedule = new WorkingSchedule();
        WorkingDay workingDay = new WorkingDay();
        workingDay.setDay(EDay.MONDAY);
        workingDay.setOpenTime(LocalTime.of(8, 0));
        workingDay.setCloseTime(LocalTime.of(17, 0));
        workingSchedule.getWorkingDays().add(workingDay);
        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 10, 18, 0);
        // When
        Boolean isAvailable = workingSchedule.isAvailableAtRequestedTime(requestedTime);
        // Then
        Assertions.assertThat(isAvailable).isFalse();
    }

    @Test
    void TestIsAvailableAtRequestedTime_TimeWithinWorkingHours_ShouldReturnTrue() {
        // Given
        WorkingSchedule schedule = new WorkingSchedule();
        WorkingDay workingDay = new WorkingDay();
        workingDay.setDay(EDay.MONDAY);
        workingDay.setOpenTime(LocalTime.of(8, 0));
        workingDay.setCloseTime(LocalTime.of(17, 0));
        schedule.getWorkingDays().add(workingDay);
        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 10, 9, 0);
        // When
        Boolean isAvailable = schedule.isAvailableAtRequestedTime(requestedTime);

        Assertions.assertThat(isAvailable).isTrue();
    }
}