package com.example.AISafePSOFT_26.Flight.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(schema = "company_flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column(nullable = false)
    private Long routeId;

    @Column(nullable = false)
    private String aircraftId;

    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    private Date scheduledDeparture;

    private Date scheduledArrival;

    public Flight() {}

    public Flight(Date scheduledArrival, Date scheduledDeparture, FlightStatus flightStatus, Long routeId, String aircraftId, Long flightId) {
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
        this.flightStatus = flightStatus;
        this.routeId = routeId;
        this.aircraftId = aircraftId;
        this.flightId = flightId;
    }
}