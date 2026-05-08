package com.example.AISafePSOFT_26.AircraftCatalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="aircraft_models_catalog")
public class AircraftModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @Column(nullable = false, unique = true)
    private String modelName;

    @ElementCollection
    @CollectionTable(
            name = "aircraft_images",
            joinColumns = @JoinColumn(name = "model_id")
    )
    @Column(name = "image_url")
    private List<String> modelImage;

    @Column(nullable = false)
    private String manufacturer;

    @Embedded
    private AircraftSpecs aircraftModelSpecs;

    protected AircraftModel() {}

    public AircraftModel(String modelName, String manufacturer,
                         AircraftSpecs aircraftModelSpecs,
                         List<String> modelImage) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.aircraftModelSpecs = aircraftModelSpecs;
        this.modelImage = modelImage;
    }
}