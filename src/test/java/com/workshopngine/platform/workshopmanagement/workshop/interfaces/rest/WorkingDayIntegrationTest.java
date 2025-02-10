package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest;

import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.CreateWorkingDayResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.CreateWorkshopResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkingDayResource;
import com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto.WorkshopResource;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkingDayIntegrationTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void canEstablishConnection() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    void TestCreateWorkingDay_ShouldPass() {
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
        // When
        ResponseEntity<WorkingDayResource> createWorkingDayResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId + "/working-days",
                HttpMethod.POST,
                new HttpEntity<>(createWorkingDayResource),
                WorkingDayResource.class
        );
        // Then
        Assertions.assertThat(createWorkingDayResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createWorkingDayResponse.getBody()).isNotNull();
        Assertions.assertThat(createWorkingDayResponse.getBody().day()).isEqualTo("MONDAY");
        Assertions.assertThat(createWorkingDayResponse.getBody().openTime()).isEqualTo(LocalTime.of(8, 0));
        Assertions.assertThat(createWorkingDayResponse.getBody().closeTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void TestGetWorkingDays_ValidWorkshopId_ShouldPass() {
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

        CreateWorkingDayResource createWorkingDayResource1 = new CreateWorkingDayResource(
                "MONDAY",
                LocalTime.of(8, 0),
                LocalTime.of(18, 0)
        );
        testRestTemplate.exchange(
                "/workshops/" + workshopId + "/working-days",
                HttpMethod.POST,
                new HttpEntity<>(createWorkingDayResource1),
                WorkingDayResource.class
        );

        CreateWorkingDayResource createWorkingDayResource2 = new CreateWorkingDayResource(
                "SATURDAY",
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
        testRestTemplate.exchange(
                "/workshops/" + workshopId + "/working-days",
                HttpMethod.POST,
                new HttpEntity<>(createWorkingDayResource2),
                WorkingDayResource.class
        );

        // When
        ResponseEntity<WorkingDayResource[]> getWorkingDaysResponse = testRestTemplate.exchange(
                "/workshops/" + workshopId + "/working-days",
                HttpMethod.GET,
                null,
                WorkingDayResource[].class
        );
        // Then
        Assertions.assertThat(getWorkingDaysResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getWorkingDaysResponse.getBody()).isNotNull();
        Assertions.assertThat(getWorkingDaysResponse.getBody().length).isEqualTo(2);
        Assertions.assertThat(getWorkingDaysResponse.getBody()[0].day()).isEqualTo("MONDAY");
        Assertions.assertThat(getWorkingDaysResponse.getBody()[0].openTime()).isEqualTo(LocalTime.of(8, 0));
        Assertions.assertThat(getWorkingDaysResponse.getBody()[0].closeTime()).isEqualTo(LocalTime.of(18, 0));
        Assertions.assertThat(getWorkingDaysResponse.getBody()[1].day()).isEqualTo("SATURDAY");
        Assertions.assertThat(getWorkingDaysResponse.getBody()[1].openTime()).isEqualTo(LocalTime.of(8, 0));
        Assertions.assertThat(getWorkingDaysResponse.getBody()[1].closeTime()).isEqualTo(LocalTime.of(12, 0));
    }
}