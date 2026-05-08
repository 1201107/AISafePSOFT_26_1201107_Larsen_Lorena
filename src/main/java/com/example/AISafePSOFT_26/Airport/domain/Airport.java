package com.example.AISafePSOFT_26.Airport.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "company_registered_airports")
public class Airport {
    @Id
    private String iataCode;

    private String airportType;

    private String name;

    @Enumerated(EnumType.STRING)
    private AirportStatus status;

    @Embedded
    private AirportLocation airportLocation;

    @Embedded
    private Facilities facilities;

    private Double routeDistance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "airport_iata_code")
    private List<Runway> airportRunways;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "airport_iata_code")
    private List<Certification> certifications;

    @ElementCollection
    @CollectionTable(
            name = "airport_contacts",
            joinColumns = @JoinColumn(name = "airport_iata_code")
    )
    private List<Contact> contacts;

    @ElementCollection
    @CollectionTable(
            name = "airport_images",
            joinColumns = @JoinColumn(name = "iata_code")
    )
    @Column(name = "image_url")
    private List<String> airportPhotos;

    public Airport(){}

    public Airport(
            String iataCode,
            String airportType,
            String name,
            AirportStatus status,
            AirportLocation airportLocation,
            Facilities facilities,
            Double routeDistance,
            List<Runway> airportRunways,
            List<Certification> certifications,
            List<Contact> contacts,
            List<String> airportPhotos
    ) {
        this.iataCode = iataCode;
        this.airportType = airportType;
        this.name = name;
        this.status = status;
        this.airportLocation = airportLocation;
        this.facilities = facilities;
        this.routeDistance = routeDistance;
        this.airportRunways = airportRunways;
        this.certifications = certifications;
        this.contacts = contacts;
        this.airportPhotos = airportPhotos;
    }
}
