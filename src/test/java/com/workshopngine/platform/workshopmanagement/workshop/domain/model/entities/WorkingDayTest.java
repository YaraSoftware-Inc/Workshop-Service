package com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

class WorkingDayTest {

    @Test
    void TestisWithinEstablishedRangeOfDays_ValidRequestedTime_ShouldPass() {
        // Given
        WorkingDay workingDay = new WorkingDay();
        workingDay.setDay(EDay.MONDAY);
        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 10, 9, 0);
        // When
        Boolean isWithinRange = workingDay.isWithinEstablishedRangeOfDays(requestedTime);
        // Then
        Assertions.assertThat(isWithinRange).isTrue();
    }

    @Test
    void TestisWithinEstablishedRangeOfDays_InvalidRequestedTime_ShouldFail() {
        // Given
        WorkingDay workingDay = new WorkingDay();
        workingDay.setDay(EDay.MONDAY);
        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 11, 9, 0);
        // When
        Boolean isWithinRange = workingDay.isWithinEstablishedRangeOfDays(requestedTime);
        // Then
        Assertions.assertThat(isWithinRange).isFalse();
    }

    @Test
    void TestisWithinEstablishedTimeRange_ValidRequestedTime_ShouldPass() {
        // Given
        WorkingDay workingDay = new WorkingDay();
        workingDay.setOpenTime(LocalTime.of(8, 0));
        workingDay.setCloseTime(LocalTime.of(17, 0));
        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 10, 9, 0);
        // When
        Boolean isWithinRange = workingDay.isWithinEstablishedTimeRange(requestedTime);
        // Then
        Assertions.assertThat(isWithinRange).isTrue();
    }

    @Test
    void TestisWithinEstablishedTimeRange_InvalidRequestedTime_ShouldFail() {
        // Given
        WorkingDay workingDay = new WorkingDay();
        workingDay.setOpenTime(LocalTime.of(8, 0));
        workingDay.setCloseTime(LocalTime.of(17, 0));
        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 10, 18, 0);
        // When
        Boolean isWithinRange = workingDay.isWithinEstablishedTimeRange(requestedTime);
        // Then
        Assertions.assertThat(isWithinRange).isFalse();
    }
}