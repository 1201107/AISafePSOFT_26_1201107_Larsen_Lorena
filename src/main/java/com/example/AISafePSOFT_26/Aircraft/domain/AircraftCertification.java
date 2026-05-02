package com.example.AISafePSOFT_26.Aircraft.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class AircraftCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftCertificationId;

    @ManyToOne
    @JoinColumn(name = "aircraft_registration_number")
    private Aircraft aircraft;

    private String name;
    private String category;

    private LocalDate startAt;
    private LocalDate expiresAt;

    protected AircraftCertification() {}
}