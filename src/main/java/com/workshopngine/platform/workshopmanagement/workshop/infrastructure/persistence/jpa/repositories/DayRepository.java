package com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.entities.Day;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.EDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    Boolean existsByName(EDay name);
    Optional<Day> findByName(EDay name);
}
