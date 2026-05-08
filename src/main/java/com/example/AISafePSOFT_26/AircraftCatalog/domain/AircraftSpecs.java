package com.example.AISafePSOFT_26.AircraftCatalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AircraftSpecs {

    @Column(nullable = false)
    private Double fuelCapacityLiters;

    @Column(nullable = false)
    private Double maximumRangeKm;

    @Column(nullable = false)
    private Double cruisingSpeedKph;

    public AircraftSpecs() {}

    public AircraftSpecs(Double fuelCapacity, Double maximumRange, Double cruisingSpeed) {
        this.fuelCapacityLiters = fuelCapacity;
        this.maximumRangeKm = maximumRange;
        this.cruisingSpeedKph = cruisingSpeed;
    }

    public Double getFuelCapacity() { return fuelCapacityLiters; }

    public Double getMaximumRange() {
        return maximumRangeKm;
    }


    public Double getCruisingSpeed() {
        return cruisingSpeedKph;
    }

}