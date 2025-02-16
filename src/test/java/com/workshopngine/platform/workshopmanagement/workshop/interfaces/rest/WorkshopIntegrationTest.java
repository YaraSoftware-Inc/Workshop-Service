package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest;

import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkshopIntegrationTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void canEstablishConnection() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    void TestCreateWorkshop_ShouldPass() {
        // Given
        CreateWorkshopResource createWorkshopResource = new CreateWorkshopResource(
                "name",
                "description",
                "address",
                "city",
                "zipCode",
                "country",
                "street",
                10,
                10,
                "1000"
        );
        // When
        ResponseEntity<WorkshopResource> createWorkshopResponse = testRestTemplate.exchange(
                "/workshops",
                HttpMethod.POST,
                new HttpEntity<>(createWorkshopResource),
                WorkshopResource.class
        );
        // Then
        Assertions.assertThat(createWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createWorkshopResponse.getBody()).isNotNull();
        Assertions.assertThat(createWorkshopResponse.getBody().status()).isEqualTo("CLOSED");
    }

    @Test
    void TestUpdateWorkshop_ShouldPass() {
        // Given
        CreateWorkshopResource createWorkshopResource = new CreateWorkshopResource(
                "name",
                "description",
                "address",
                "street",
                "city",
                "zip code",
                "country",
                10,
                10,
                "1000"
        );
        ResponseEntity<WorkshopResource> createWorkshopResponse = testRestTemplate.exchange(
                "/workshops",
                HttpMethod.POST,
                new HttpEntity<>(createWorkshopResource),
                WorkshopResource.class
        );

        Assumptions.assumeThat(createWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String workshopId = Objects.requireNonNull(createWorkshopResponse.getBody()).id();

        UpdateWorkshopResource updateWorkshopResource = new UpdateWorkshopResource(
                "new name",
                "new description",
                "new address",
                "new street",
                "new city",
                "new zipCode",
                "new country",
                40,
                50
        );
        // When
        ResponseEntity<WorkshopResource> updateWorkshopResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId,
                HttpMethod.PUT,
                new HttpEntity<>(updateWorkshopResource),
                WorkshopResource.class
        );

        // Then
        Assertions.assertThat(updateWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updateWorkshopResponse.getBody()).isNotNull();
        Assertions.assertThat(updateWorkshopResponse.getBody().name()).isEqualTo("new name");
        Assertions.assertThat(updateWorkshopResponse.getBody().description()).isEqualTo("new description");
        Assertions.assertThat(updateWorkshopResponse.getBody().address()).isEqualTo("new address");
        Assertions.assertThat(updateWorkshopResponse.getBody().city()).isEqualTo("new city");
        Assertions.assertThat(updateWorkshopResponse.getBody().zipCode()).isEqualTo("new zipCode");
        Assertions.assertThat(updateWorkshopResponse.getBody().country()).isEqualTo("new country");
        Assertions.assertThat(updateWorkshopResponse.getBody().street()).isEqualTo("new street");
        Assertions.assertThat(updateWorkshopResponse.getBody().maxCapacityVehicle()).isEqualTo(40);
        Assertions.assertThat(updateWorkshopResponse.getBody().maxCapacityMechanic()).isEqualTo(50);
    }

    @Test
    void TestUpdateByFieldsWorkshop_ShouldPass() {
        // Given
        CreateWorkshopResource createWorkshopResource = new CreateWorkshopResource(
                "name",
                "description",
                "address",
                "street",
                "city",
                "zip code",
                "country",
                10,
                10,
                "1000"
        );
        ResponseEntity<WorkshopResource> createWorkshopResponse = testRestTemplate.exchange(
                "/workshops",
                HttpMethod.POST,
                new HttpEntity<>(createWorkshopResource),
                WorkshopResource.class
        );

        Assumptions.assumeThat(createWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String workshopId = Objects.requireNonNull(createWorkshopResponse.getBody()).id();

        UpdateWorkshopByFieldsResource updateWorkshopResource = new UpdateWorkshopByFieldsResource(
                "OPEN"
        );
        // When
        ResponseEntity<WorkshopResource> updateWorkshopResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId,
                HttpMethod.PATCH,
                new HttpEntity<>(updateWorkshopResource),
                WorkshopResource.class
        );

        // Then
        Assertions.assertThat(updateWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updateWorkshopResponse.getBody()).isNotNull();
        Assertions.assertThat(updateWorkshopResponse.getBody().status()).isEqualTo("OPEN");
    }

    @Test
    void TestGetWorkshop_ValidId_ShouldPass() {
        // Given
        CreateWorkshopResource createWorkshopResource = new CreateWorkshopResource(
                "name",
                "description",
                "address",
                "street",
                "city",
                "zip code",
                "country",
                10,
                10,
                "1000"
        );
        ResponseEntity<WorkshopResource> createWorkshopResponse = testRestTemplate.exchange(
                "/workshops",
                HttpMethod.POST,
                new HttpEntity<>(createWorkshopResource),
                WorkshopResource.class
        );

        Assumptions.assumeThat(createWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String workshopId = Objects.requireNonNull(createWorkshopResponse.getBody()).id();

        // When
        ResponseEntity<WorkshopResource> getWorkshopResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId,
                HttpMethod.GET,
                null,
                WorkshopResource.class
        );

        // Then
        Assertions.assertThat(getWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getWorkshopResponse.getBody()).isNotNull();
        Assertions.assertThat(getWorkshopResponse.getBody().id()).isEqualTo(workshopId);
        Assertions.assertThat(getWorkshopResponse.getBody().name()).isEqualTo("name");
        Assertions.assertThat(getWorkshopResponse.getBody().description()).isEqualTo("description");
        Assertions.assertThat(getWorkshopResponse.getBody().address()).isEqualTo("address");
        Assertions.assertThat(getWorkshopResponse.getBody().city()).isEqualTo("city");
        Assertions.assertThat(getWorkshopResponse.getBody().zipCode()).isEqualTo("zip code");
        Assertions.assertThat(getWorkshopResponse.getBody().country()).isEqualTo("country");
        Assertions.assertThat(getWorkshopResponse.getBody().street()).isEqualTo("street");
        Assertions.assertThat(getWorkshopResponse.getBody().maxCapacityVehicle()).isEqualTo(10);
        Assertions.assertThat(getWorkshopResponse.getBody().maxCapacityMechanic()).isEqualTo(10);
        Assertions.assertThat(getWorkshopResponse.getBody().status()).isEqualTo("CLOSED");
        Assertions.assertThat(getWorkshopResponse.getBody().ownerId()).isEqualTo("1000");
    }

    @Test
    void TestGetWorkshop_ValidOwnerId_ShouldPass(){
        // Given
        CreateWorkshopResource createWorkshopResource = new CreateWorkshopResource(
                "name",
                "description",
                "address",
                "street",
                "city",
                "zip code",
                "country",
                10,
                10,
                "c78f013e-1f65-407d-8c7b-e6a75642d451"
        );
        ResponseEntity<WorkshopResource> createWorkshopResponse = testRestTemplate.exchange(
                "/workshops",
                HttpMethod.POST,
                new HttpEntity<>(createWorkshopResource),
                WorkshopResource.class
        );

        Assumptions.assumeThat(createWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String ownerId = Objects.requireNonNull(createWorkshopResponse.getBody()).ownerId();

        // When
        ResponseEntity<WorkshopResource> getWorkshopResponse = testRestTemplate.exchange(
                "/workshops?ownerId=" + ownerId,
                HttpMethod.GET,
                null,
                WorkshopResource.class
        );

        // Then
        Assertions.assertThat(getWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getWorkshopResponse.getBody()).isNotNull();
        Assertions.assertThat(getWorkshopResponse.getBody().name()).isEqualTo(createWorkshopResource.name());
        Assertions.assertThat(getWorkshopResponse.getBody().description()).isEqualTo(createWorkshopResource.description());
        Assertions.assertThat(getWorkshopResponse.getBody().address()).isEqualTo(createWorkshopResource.address());
        Assertions.assertThat(getWorkshopResponse.getBody().city()).isEqualTo(createWorkshopResource.city());
        Assertions.assertThat(getWorkshopResponse.getBody().zipCode()).isEqualTo(createWorkshopResource.zipCode());
        Assertions.assertThat(getWorkshopResponse.getBody().country()).isEqualTo(createWorkshopResource.country());
        Assertions.assertThat(getWorkshopResponse.getBody().street()).isEqualTo(createWorkshopResource.street());
        Assertions.assertThat(getWorkshopResponse.getBody().maxCapacityVehicle()).isEqualTo(createWorkshopResource.maxCapacityVehicle());
        Assertions.assertThat(getWorkshopResponse.getBody().maxCapacityMechanic()).isEqualTo(createWorkshopResource.maxCapacityMechanic());
        Assertions.assertThat(getWorkshopResponse.getBody().status()).isEqualTo("CLOSED");
        Assertions.assertThat(getWorkshopResponse.getBody().ownerId()).isEqualTo(ownerId);
    }

    @Test
    void TestIsWorkshopAvailable_ValidIdAndDate_ShouldPass() {
        // Given
        CreateWorkshopResource createWorkshopResource = new CreateWorkshopResource(
                "name",
                "description",
                "address",
                "street",
                "city",
                "zip code",
                "country",
                10,
                10,
                "1000"
        );
        ResponseEntity<WorkshopResource> createWorkshopResponse = testRestTemplate.exchange(
                "/workshops",
                HttpMethod.POST,
                new HttpEntity<>(createWorkshopResource),
                WorkshopResource.class
        );

        Assumptions.assumeThat(createWorkshopResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String workshopId = Objects.requireNonNull(createWorkshopResponse.getBody()).id();

        CreateWorkingDayResource createWorkingDayResource = new CreateWorkingDayResource(
                "MONDAY",
                LocalTime.of(8, 0),
                LocalTime.of(18, 0)
        );

        ResponseEntity<WorkingDayResource> createWorkingDayResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId + "/working-days",
                HttpMethod.POST,
                new HttpEntity<>(createWorkingDayResource),
                WorkingDayResource.class
        );

        Assumptions.assumeThat(createWorkingDayResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        LocalDateTime requestedTime = LocalDateTime.of(2025, 2, 10, 12, 0);

        // When
        ResponseEntity<WorkshopAvailabilityResource> isWorkshopAvailableResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId + "/availability?requestedTime=" + requestedTime,
                HttpMethod.GET,
                null,
                WorkshopAvailabilityResource.class
        );

        // Then
        Assertions.assertThat(isWorkshopAvailableResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(isWorkshopAvailableResponse.getBody()).isNotNull();
        Assertions.assertThat(isWorkshopAvailableResponse.getBody().available()).isTrue();
    }
}