package com.example.AISafePSOFT_26.Route.domain;

import com.example.AISafePSOFT_26.Airport.domain.Airport;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "company_routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @Version
    private Long version;

    @Column(nullable = false)
    private String routeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RouteStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RouteType type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_airport_id")
    private Airport originAirport;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport_id")
    private Airport destinationAirport;

    @Embedded
    private RouteHistory routeHistory;

    @Column(nullable = false)
    private Double estimatedFlightTimeHours;

    @Embedded
    private RouteRequirements routeRequirements;

    protected Route() {
    }

    public Route(RouteRequirements routeRequirements, RouteStatus status, RouteType type,
            RouteHistory routeHistory, Double estimatedFlightTimeHours, Airport originAirport,
            Airport destinationAirport,String routeName) {
        this.routeRequirements = routeRequirements;
        this.status = status;
        this.type = type;
        this.routeHistory = routeHistory;
        this.estimatedFlightTimeHours = estimatedFlightTimeHours;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.routeName = routeName;
    }

    public void activateRoute() {
        if (status == RouteStatus.ARCHIVED) {
            throw new IllegalStateException(
                    "Retired routes cannot be activated"
            );
        }
        this.status = RouteStatus.ACTIVE;
    }

    public void suspendRoute() {
        if (status == RouteStatus.ARCHIVED) {
            throw new IllegalStateException(
                    "Archived routes cannot be suspended"
            );
        }

        this.status = RouteStatus.INACTIVE;
    }

    public void retireRoute() {
        if (routeHistory != null) {
            routeHistory.endRoute(LocalDate.now());
        }
        this.status = RouteStatus.ARCHIVED;
    }

    public void updateEstimatedFlightTime(Double estimatedFlightTimeHours) {
        if (estimatedFlightTimeHours == null
                || estimatedFlightTimeHours <= 0) {

            throw new IllegalArgumentException(
                    "Estimated flight time must be positive"
            );
        }
        this.estimatedFlightTimeHours =
                estimatedFlightTimeHours;
    }

    public void changeDestination(Airport newDestination) {
        if (newDestination == null) {
            throw new IllegalArgumentException(
                    "Destination airport cannot be null"
            );
        }
        if (newDestination.equals(originAirport)) {
            throw new IllegalArgumentException(
                    "Destination airport cannot equal origin airport"
            );
        }
        this.destinationAirport = newDestination;
    }

    public boolean isOperational() {

        return status == RouteStatus.ACTIVE;
    }

    public Long getRouteId() {
        return routeId;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public RouteType getType() {
        return type;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public RouteHistory getRouteHistory() {
        return routeHistory;
    }

    public Double getEstimatedFlightTimeHours() {
        return estimatedFlightTimeHours;
    }

    public RouteRequirements getRouteRequirements() {
        return routeRequirements;
    }

    public String getRouteName() {
        return routeName;
    }

    public Long getVersion() {
        return version;
    }
}
