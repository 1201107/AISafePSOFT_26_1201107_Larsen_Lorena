package com.example.AISafePSOFT_26.Flight.domain;

import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Route.domain.Route;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Version
    private Long version;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_registration")
    private Aircraft aircraft;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus flightStatus;

    @Column(nullable = false)
    private LocalDateTime scheduledDeparture;

    @Column(nullable = false)
    private LocalDateTime scheduledArrival;

    protected Flight() {
    }

    public Flight(Route route, Aircraft aircraft, LocalDateTime scheduledDeparture,
            LocalDateTime scheduledArrival) {
        this.flightStatus=FlightStatus.SCHEDULED;
        this.route = route;
        this.aircraft = aircraft;
        this.scheduledDeparture = scheduledDeparture;
        this.scheduledArrival = scheduledArrival;
    }

    public void updateAircraft(Aircraft aircraft) {
        if (aircraft == null) {
            throw new IllegalArgumentException(
                    "Aircraft cannot be null"
            );
        }
        if (this.flightStatus == FlightStatus.ARRIVED) {
            throw new IllegalStateException(
                    "Cannot change aircraft of completed flight"
            );
        }
        this.aircraft = aircraft;
    }

    public void changeRoute(Route route) {
        if (route == null) {
            throw new IllegalArgumentException(
                    "Route cannot be null"
            );
        }
        if (this.flightStatus == FlightStatus.ARRIVED) {
            throw new IllegalStateException(
                    "Cannot change route of completed flight"
            );
        }
        this.route = route;
    }

    public void startFlight() {
        if (flightStatus != FlightStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Flight cannot be started"
            );
        }
        this.flightStatus = FlightStatus.IN_PROGRESS;
    }

    public void completeFlight() {
        if (flightStatus != FlightStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Flight is not in progress"
            );
        }
        this.flightStatus = FlightStatus.ARRIVED;
    }

    public void cancelFlight() {
        if (flightStatus == FlightStatus.ARRIVED) {
            throw new IllegalStateException(
                    "Completed flight cannot be cancelled"
            );
        }
        this.flightStatus = FlightStatus.CANCELED;
    }

    public boolean isActive() {
        return flightStatus == FlightStatus.IN_PROGRESS;
    }

    public Long getFlightId() {
        return flightId;
    }

    public Route getRoute() {
        return route;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public LocalDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }
}