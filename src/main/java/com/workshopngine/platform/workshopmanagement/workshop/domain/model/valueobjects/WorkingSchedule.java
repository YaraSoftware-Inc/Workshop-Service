package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.Day;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Collection;

@Embeddable
@Getter
@Setter
public class WorkingSchedule {
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Day> workingDays;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    public WorkingSchedule() {
    }

    public WorkingSchedule(Collection<String> workingDays, LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.workingDays = workingDays.stream().map(Day::new).toList();
    }
}
