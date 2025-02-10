package com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities;

import com.workshopngine.platform.workshopmanagement.shared.domain.model.entities.AuditableModel;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkingDayCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class WorkingDay extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @Enumerated(EnumType.STRING)
    private EDay day;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    public WorkingDay() {
        super();
        this.openTime = null;
        this.closeTime = null;
    }

    public WorkingDay(Workshop workshop, CreateWorkingDayCommand command) {
        this();
        this.workshop = workshop;
        this.day = command.day();
        this.openTime = command.openTime();
        this.closeTime = command.closeTime();
    }

    public Boolean isWithinEstablishedRangeOfDays(LocalDateTime requestedTime) {
        DayOfWeek requestedDate = requestedTime.toLocalDate().getDayOfWeek();
        return this.day.equals(EDay.valueOf(requestedDate.name()));
    }

    public Boolean isWithinEstablishedTimeRange(LocalDateTime requestedTime) {
        return requestedTime.toLocalTime().isAfter(this.openTime) && requestedTime.toLocalTime().isBefore(this.closeTime);
    }
}
