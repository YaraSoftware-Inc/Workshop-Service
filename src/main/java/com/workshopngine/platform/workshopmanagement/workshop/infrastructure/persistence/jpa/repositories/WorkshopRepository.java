package com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.OwnerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, String> {
    Optional<Workshop> findByOwnerId(OwnerId ownerId);
}
