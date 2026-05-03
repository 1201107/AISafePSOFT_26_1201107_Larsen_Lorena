package com.example.AISafePSOFT_26.Route;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class RouteHistory {
    private LocalDate routeBegin;
    private LocalDate routeFinish;
    private Integer routeUsage;

    public RouteHistory() {}

    public RouteHistory(LocalDate routeBegin, LocalDate routeFinish, Integer routeUsage) {
        this.routeBegin = routeBegin;
        this.routeFinish = routeFinish;
        this.routeUsage = routeUsage;
    }

    private void updateRouteUsage(Integer routeUsage) {
        this.routeUsage += routeUsage;
    }
}
