package com.example.AISafePSOFT_26.Airport.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "company_airports_certifications")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    private String name;

    private String category;

    private LocalDate startsAt;

    private LocalDate expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_iata_code")
    private Airport airport;

    protected Certification() {}

    public Certification(
            String name,
            String category,
            LocalDate startsAt,
            LocalDate expiresAt
    ) {
        this.name = name;
        this.category = category;
        this.startsAt = startsAt;
        this.expiresAt = expiresAt;
    }

    public void setAirport(Airport airport) {
    }
}