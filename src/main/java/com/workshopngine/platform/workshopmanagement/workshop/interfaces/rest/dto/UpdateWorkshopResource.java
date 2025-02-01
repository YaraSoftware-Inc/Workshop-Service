package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

import java.time.LocalTime;
import java.util.Collection;

public record UpdateWorkshopResource(
    String name,
    String description,
    String address,
    String street,
    String city,
    String zipCode,
    String country,
    Integer maxCapacityVehicle,
    Integer maxCapacityMechanic,
    LocalTime openTime,
    LocalTime closeTime,
    Collection<String> workingDays
) {
}
