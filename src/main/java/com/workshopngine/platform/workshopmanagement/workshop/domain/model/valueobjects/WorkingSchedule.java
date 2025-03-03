package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkingDayCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.WorkingDay;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Embeddable
@Getter
@Setter
public class WorkingSchedule {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "workshop")
    private Collection<WorkingDay> workingDays;

    public WorkingSchedule() {
        this.workingDays = new ArrayList<>();
    }

    public WorkingDay addWorkingDay(Workshop workshop, CreateWorkingDayCommand command) {
        var newWorkingDay = new WorkingDay(workshop, command);
        this.workingDays.add(newWorkingDay);
        return newWorkingDay;
    }

    public Boolean isAvailableAtRequestedTime(LocalDateTime requestedTime) {
        WorkingDay requestedWorkingDay = this.workingDays.stream().filter(day -> day.isWithinEstablishedRangeOfDays(requestedTime)).findFirst().orElse(null);
        if (requestedWorkingDay == null) return false;
        return requestedWorkingDay.isWithinEstablishedTimeRange(requestedTime);
    }
}
