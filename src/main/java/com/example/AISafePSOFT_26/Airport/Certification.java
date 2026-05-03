package com.example.AISafePSOFT_26.Airport;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    private String name;

    private String category;

    private LocalDate startsAt;

    private LocalDate expiresAt;

    public Certification() {}

    public Certification(Long certificationId, String name, String category, LocalDate expiresAt, LocalDate startsAt) {
        this.certificationId = certificationId;
        this.name = name;
        this.category = category;
        this.expiresAt = expiresAt;
        this.startsAt = startsAt;
    }
}
