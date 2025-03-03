package com.workshopngine.platform.workshopmanagement.workshop.application.internal.commandservices;

import com.workshopngine.platform.workshopmanagement.workshop.domain.model.aggregates.Workshop;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkingDayCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.CreateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.commands.UpdateWorkshopCommand;
import com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects.*;
import com.workshopngine.platform.workshopmanagement.workshop.domain.services.WorkshopCommandService;
import com.workshopngine.platform.workshopmanagement.workshop.infrastructure.persistence.jpa.repositories.WorkshopRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class WorkshopCommandServiceImplTest {
    @Mock
    private WorkshopRepository workshopRepository;

    @Captor
    private ArgumentCaptor<Workshop> workshopArgumentCaptor;

    private WorkshopCommandService workshopCommandService;

    @BeforeEach
    void setUp() {
        workshopCommandService = new WorkshopCommandServiceImpl(workshopRepository);
    }

    @Test
    void TestCreateWorkshop_ValidData_ShouldPass() {
        // Given
        CreateWorkshopCommand command = new CreateWorkshopCommand(
                new WorkshopInfo("name", "description"),
                new Location("address", "street", "city", "zipCode", "country"),
                new Capacity(10, 20),
                new OwnerId("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6")
        );
        // When
        workshopCommandService.handle(command);
        // Then
        Mockito.verify(workshopRepository).save(workshopArgumentCaptor.capture());
        Workshop workshop = workshopArgumentCaptor.getValue();
        Assertions.assertThat(workshop.getInformation())
                .as("Workshop information")
                .isNotNull()
                .extracting("name", "description")
                .containsExactly("name", "description");
        Assertions.assertThat(workshop.getLocation())
                .as("Workshop location")
                .isNotNull()
                .extracting("address", "street", "city", "zipCode", "country")
                .containsExactly("address", "street", "city", "zipCode", "country");
        Assertions.assertThat(workshop.getCapacity())
                .as("Workshop capacity")
                .isNotNull()
                .extracting("maxCapacityVehicle", "maxCapacityMechanic")
                .containsExactly(10, 20);
        Assertions.assertThat(workshop.getOwnerId())
                .as("Workshop owner ID")
                .isNotNull()
                .extracting("ownerId")
                .isEqualTo("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6");
        Assertions.assertThat(workshop.getStatus())
                .as("Workshop status")
                .isEqualTo(EWorkshopStatus.CLOSED);
    }

    @Test
    void TestUpdateWorkshop_ValidId_ShouldPass() {
        // Given
        Workshop existingWorkshop = new Workshop(
                new CreateWorkshopCommand(
                        new WorkshopInfo("name", "description"),
                        new Location("address", "street", "city", "zipCode", "country"),
                        new Capacity(10, 20),
                        new OwnerId("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6")
                )
        );
        Mockito.when(workshopRepository.findById("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"))
                .thenReturn(Optional.of(existingWorkshop));
        UpdateWorkshopCommand command = new UpdateWorkshopCommand(
                "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6",
                new WorkshopInfo("new name", "new description"),
                new Location("new address", "new street", "new city", "new zipCode", "new country"),
                new Capacity(30, 40)
        );
        // When
        workshopCommandService.handle(command);
        // Then
        Mockito.verify(workshopRepository).save(workshopArgumentCaptor.capture());
        Workshop workshop = workshopArgumentCaptor.getValue();
        Assertions.assertThat(workshop.getInformation())
                .as("Workshop information")
                .isNotNull()
                .extracting("name", "description")
                .containsExactly("new name", "new description");
        Assertions.assertThat(workshop.getLocation())
                .as("Workshop location")
                .isNotNull()
                .extracting("address", "street", "city", "zipCode", "country")
                .containsExactly("new address", "new street", "new city", "new zipCode", "new country");
        Assertions.assertThat(workshop.getCapacity())
                .as("Workshop capacity")
                .isNotNull()
                .extracting("maxCapacityVehicle", "maxCapacityMechanic")
                .containsExactly(30, 40);
    }

    @Test
    void TestUpdateWorkshop_InvalidId_ShouldThrowIllegalArgumentException() {
        // Given
        UpdateWorkshopCommand command = new UpdateWorkshopCommand(
                "1a2b3c4d-5e6f-7g8h-9i0j-b1b2c3d4e5f6",
                new WorkshopInfo("new name", "new description"),
                new Location("new address", "new street", "new city", "new zipCode", "new country"),
                new Capacity(30, 40)
        );
        Mockito.when(workshopRepository.findById(anyString()))
                .thenThrow(new IllegalArgumentException("Workshop with ID %s not found".formatted(command.workshopId())));
        // When
        // Then
        Assertions.assertThatThrownBy(() ->
                workshopCommandService.handle(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Workshop with ID %s not found".formatted(command.workshopId()));
    }

    @Test
    void TestCreateWorkingDay_ValidWorkshopId_ShouldPass() {
        // Given
        Workshop existingWorkshop = new Workshop(
                new CreateWorkshopCommand(
                        new WorkshopInfo("name", "description"),
                        new Location("address", "street", "city", "zipCode", "country"),
                        new Capacity(10, 20),
                        new OwnerId("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6")
                )
        );
        Mockito.when(workshopRepository.findById("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"))
                .thenReturn(Optional.of(existingWorkshop));
        CreateWorkingDayCommand command = new CreateWorkingDayCommand(
                "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6",
                EDay.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(17, 0)
        );
        // When
        workshopCommandService.handle(command);
        // Then
        Mockito.verify(workshopRepository).save(workshopArgumentCaptor.capture());
        Workshop workshop = workshopArgumentCaptor.getValue();
        Assertions.assertThat(workshop.getWorkingSchedule().getWorkingDays())
                .as("Workshop working days")
                .isNotEmpty();
    }
}