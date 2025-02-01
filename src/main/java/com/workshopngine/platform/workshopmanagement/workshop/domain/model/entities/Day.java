package com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EDay name;

    public Day() {
    }

    public Day(String name) {
        this.name = EDay.valueOf(name);
    }

    public Day(EDay day) {
        this.name = day;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
