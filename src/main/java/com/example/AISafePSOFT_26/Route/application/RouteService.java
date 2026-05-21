package com.example.AISafePSOFT_26.Route.application;

import com.example.AISafePSOFT_26.Airport.domain.Airport;
import com.example.AISafePSOFT_26.Airport.domain.AirportStatus;
import com.example.AISafePSOFT_26.Airport.infrastructure.AirportRepository;
import com.example.AISafePSOFT_26.Route.domain.Route;
import com.example.AISafePSOFT_26.Route.domain.RouteHistory;
import com.example.AISafePSOFT_26.Route.domain.RouteRequirements;
import com.example.AISafePSOFT_26.Route.domain.RouteStatus;
import com.example.AISafePSOFT_26.Route.domain.RouteType;
import com.example.AISafePSOFT_26.Route.infrastructure.RouteRepository;
import com.example.AISafePSOFT_26.UseCase;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

@UseCase
public class RouteService {
    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;

    public RouteService(RouteRepository routeRepository, AirportRepository airportRepository) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
    }

    public Route createRoute(String routeName, String originIataCode, String destinationIataCode,
                             Double estimatedFlightTimeHours, RouteType type,
                             RouteRequirements requirements) {
        validateRouteName(routeName);
        validateFlightTime(estimatedFlightTimeHours);
        validateDifferentAirports(originIataCode, destinationIataCode);

        if (routeRepository.findByRouteName(routeName).isPresent()) {
            throw new DomainException("Route already exists");
        }

        Airport originAirport = findOperationalAirport(originIataCode, "Origin airport does not exist");
        Airport destinationAirport = findOperationalAirport(destinationIataCode, "Destination airport does not exist");

        Route route = new Route(
                requirements,
                RouteStatus.ACTIVE,
                type == null ? RouteType.DIRECT : type,
                new RouteHistory(LocalDate.now(), 0),
                estimatedFlightTimeHours,
                originAirport,
                destinationAirport,
                routeName
        );

        return routeRepository.save(route);
    }

    public Route getRouteDetails(Long routeId) {
        return findRoute(routeId);
    }

    public RouteHistory getRouteHistory(Long routeId) {
        return findRoute(routeId).getRouteHistory();
    }

    public List<Route> getRoutesFromAirport(String airportIataCode) {
        validateIataCode(airportIataCode);
        if (airportRepository.findByIataCode(airportIataCode).isEmpty()) {
            throw new DomainException("Airport does not exist");
        }
        return routeRepository.findByOriginAirport_IataCode(airportIataCode);
    }

    public List<Route> searchRoutes(String originIataCode, String destinationIataCode) {
        Specification<Route> spec = (root, query, cb) -> cb.conjunction();

        if (originIataCode != null) {
            validateIataCode(originIataCode);
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("originAirport").get("iataCode"), originIataCode));
        }

        if (destinationIataCode != null) {
            validateIataCode(destinationIataCode);
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("destinationAirport").get("iataCode"), destinationIataCode));
        }

        return routeRepository.findAll(spec);
    }

    public Route updateRoute(Long routeId, String destinationIataCode,
                             Double estimatedFlightTimeHours, RouteStatus status) {
        Route route = findRoute(routeId);

        if (destinationIataCode != null) {
            validateDifferentAirports(route.getOriginAirport().getIataCode(), destinationIataCode);
            Airport destinationAirport = findOperationalAirport(
                    destinationIataCode,
                    "Destination airport does not exist"
            );
            route.changeDestination(destinationAirport);
        }

        if (estimatedFlightTimeHours != null) {
            route.updateEstimatedFlightTime(estimatedFlightTimeHours);
        }

        if (status != null) {
            changeStatus(route, status);
        }

        return routeRepository.save(route);
    }

    private Route findRoute(Long routeId) {
        return routeRepository.findByRouteId(routeId)
                .orElseThrow(() -> new DomainException("Route does not exist"));
    }

    private Airport findOperationalAirport(String iataCode, String notFoundMessage) {
        validateIataCode(iataCode);
        Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new DomainException(notFoundMessage));

        if (airport.getStatus() != AirportStatus.OPERATIONAL) {
            throw new DomainException("Airport is not operational");
        }

        return airport;
    }

    private void changeStatus(Route route, RouteStatus status) {
        if (status == RouteStatus.ACTIVE) {
            route.activateRoute();
        } else if (status == RouteStatus.INACTIVE) {
            route.suspendRoute();
        } else if (status == RouteStatus.ARCHIVED) {
            route.retireRoute();
        }
    }

    private void validateRouteName(String routeName) {
        if (routeName == null || routeName.isBlank()) {
            throw new DomainException("Route name is required");
        }
    }

    private void validateFlightTime(Double estimatedFlightTimeHours) {
        if (estimatedFlightTimeHours == null || estimatedFlightTimeHours <= 0) {
            throw new DomainException("Estimated flight time must be positive");
        }
    }

    private void validateDifferentAirports(String originIataCode, String destinationIataCode) {
        if (originIataCode != null && originIataCode.equals(destinationIataCode)) {
            throw new DomainException("Origin and destination airports must be different");
        }
    }

    private void validateIataCode(String iataCode) {
        if (iataCode == null || !iataCode.matches("[A-Z]{3}")) {
            throw new DomainException("IATA code must have 3 uppercase letters");
        }
    }
}
