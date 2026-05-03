package com.example.AISafePSOFT_26.Airport;

import jakarta.persistence.*;

@Entity
public class Runway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long runwayId;

    private String runwayName;

    private Double length;

    private String orientation;

    @Enumerated(EnumType.STRING)
    private RunwayStatus status;

    public Runway() {}

    public Runway(Long runwayId, String runwayName, Double length, String orientation, RunwayStatus status) {
        this.runwayId = runwayId;
        this.runwayName = runwayName;
        this.length = length;
        this.orientation = orientation;
        this.status = status;
    }
}