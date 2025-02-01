package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

public record UpdateWorkshopResource(
    String name,
    String description,
    String address,
    String street,
    String city,
    String zipCode,
    String country,
    Integer maxCapacityVehicle,
    Integer maxCapacityMechanic
) {
}
