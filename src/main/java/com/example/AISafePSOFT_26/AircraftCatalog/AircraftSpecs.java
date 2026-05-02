package com.example.AISafePSOFT_26.AircraftCatalog;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AircraftSpecs {

    @Column(nullable = false)
    private Double fuelCapacity;

    @Column(nullable = false)
    private Double maximumRange;

    @Column(nullable = false)
    private Double cruisingSpeed;

    public AircraftSpecs() {}

    public AircraftSpecs(Double fuelCapacity, Double maximumRange, Double cruisingSpeed) {
        this.fuelCapacity = fuelCapacity;
        this.maximumRange = maximumRange;
        this.cruisingSpeed = cruisingSpeed;
    }

    public Double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Double getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(Double maximumRange) {
        this.maximumRange = maximumRange;
    }

    public Double getCruisingSpeed() {
        return cruisingSpeed;
    }

    public void setCruisingSpeed(Double cruisingSpeed) {
        this.cruisingSpeed = cruisingSpeed;
    }
}