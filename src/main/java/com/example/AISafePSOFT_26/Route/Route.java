package com.example.AISafePSOFT_26.Route;

import jakarta.persistence.*;

@Entity
@Table(schema = "company_routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @Enumerated(EnumType.STRING)
    private RouteStatus status;

    @Enumerated(EnumType.STRING)
    private RouteType type;

    private String originIataCode;

    private String destinationIataCode;

    @Embedded
    private RouteHistory routeHistory;

    private Double estimatedFlightTime;

    public Route() {}

    public Route(Long routeId, RouteStatus status, String originIataCode, RouteType type, String destinationIataCode, RouteHistory routeHistory, Double estimatedFlightTime) {
        this.routeId = routeId;
        this.status = status;
        this.originIataCode = originIataCode;
        this.type = type;
        this.destinationIataCode = destinationIataCode;
        this.routeHistory = routeHistory;
        this.estimatedFlightTime = estimatedFlightTime;
    }
}
