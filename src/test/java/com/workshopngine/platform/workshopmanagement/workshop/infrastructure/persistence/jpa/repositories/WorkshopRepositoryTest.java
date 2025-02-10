package com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.Capacity;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.Location;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.OwnerId;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.WorkshopInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkshopRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

    @Autowired
    private WorkshopRepository workshopRepository;

    @Test
    void TestFindWorkshop_ValidOwnerId_ShouldPass() {
        // Given
        var ownerId = "1000";
        var command = new CreateWorkshopCommand(
                new WorkshopInfo("name", "description"),
                new Location("address", "city", "zipCode", "country", "street"),
                new Capacity(10, 10),
                new OwnerId(ownerId)
        );
        var workshop = new Workshop(command);
        workshopRepository.save(workshop);
        // When
        var result = workshopRepository.findByOwnerId(new OwnerId(ownerId));
        // Then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getOwnerId().ownerId()).isEqualTo(ownerId);
    }
}