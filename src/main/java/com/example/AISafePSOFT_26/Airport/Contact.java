package com.example.AISafePSOFT_26.Airport;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Contact {
    @Enumerated(EnumType.STRING)
    private ContactType type;
    @Column(nullable = false)
    private String value;
}