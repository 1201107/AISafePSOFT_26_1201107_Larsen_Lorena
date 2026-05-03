package com.example.AISafePSOFT_26.Airport;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema="company_registered_airports")
public class Airport {
    @Id
    private String iataCode;

    private String airportType;

    private String name;

    private AirportStatus status;

    private AirportLocation airportLocation;

    private Double routeDistance;

    @ElementCollection
    private List<Long> airportRunways;

    @ElementCollection
    @CollectionTable(
            name = "airport_images",
            joinColumns = @JoinColumn(name = "iata_code")
    )
    @Column(name = "image_url")
    private List<String> airportPhotos;

}
