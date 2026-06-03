package com.example.AISafePSOFT_26.Route.infrastructure;

import com.example.AISafePSOFT_26.Route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long>, JpaSpecificationExecutor<Route> {
    Optional<Route> findByRouteId(Long routeId);
    Optional<Route> findByRouteName(String routeName);
    List<Route> findByOriginAirport_IataCode(String originIataCode);
    List<Route> findByOriginAirport_IataCodeAndDestinationAirport_IataCode(
            String originIataCode,
            String destinationIataCode
    );

    List<Route> findByOriginAirport_IataCodeOrDestinationAirport_IataCode(
            String originIataCode,
            String destinationIataCode
    );
}
