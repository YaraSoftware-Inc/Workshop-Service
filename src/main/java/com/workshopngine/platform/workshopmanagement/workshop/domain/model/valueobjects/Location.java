package com.workshopngine.platform.workshopmanagement.workshop.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Location(String address, String street, String city, String zipCode, String country) {
    public Location() { this(null, null, null, null, null); }

    public Location {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or blank");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("Zip code cannot be null or blank");
        }
    }
}