package com.example.AISafePSOFT_26.Route.infrastructure;

import com.example.AISafePSOFT_26.Route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByRouteId(Long RouteId);
    Optional<Route> findByRouteName(String RouteName);
}
