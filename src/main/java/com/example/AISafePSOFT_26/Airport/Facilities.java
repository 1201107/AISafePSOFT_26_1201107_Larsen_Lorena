package com.example.AISafePSOFT_26.Airport;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Facilities {
    @Column(nullable = false)
    private Integer terminalCount;

    @Column(nullable = false)
    private Integer gateCount;

    @ElementCollection
    private List<String> services= new ArrayList<>();

    public Facilities() {}

    public Facilities(Integer terminalCount, List<String> services, Integer gateCount) {
        this.terminalCount = terminalCount;
        this.services = services;
        this.gateCount = gateCount;
    }
}
