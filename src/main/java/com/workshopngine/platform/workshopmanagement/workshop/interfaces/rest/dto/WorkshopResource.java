package com.workshopngine.platform.workshopmanagement.workshop.interfaces.rest.dto;

public record WorkshopResource(
    Long id,
    String name,
    String description,
    String address,
    String street,
    String city,
    String zipCode,
    String country,
    Integer maxCapacityVehicle,
    Integer maxCapacityMechanic,
    Long ownerId,
    String status
) {
}
