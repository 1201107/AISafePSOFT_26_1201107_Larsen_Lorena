package com.example.AISafePSOFT_26.Route;

import com.example.AISafePSOFT_26.Route.application.RouteService;
import com.example.AISafePSOFT_26.Route.domain.Route;
import com.example.AISafePSOFT_26.Route.domain.RouteHistory;
import com.example.AISafePSOFT_26.Route.domain.RouteRequirements;
import com.example.AISafePSOFT_26.Route.domain.RouteStatus;
import com.example.AISafePSOFT_26.Route.domain.RouteType;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse createRoute(@Valid @RequestBody AddRouteRequest request) {
        if (request.requirements() == null) {
            throw new DomainException("Route requirements are required");
        }

        RouteRequirements requirements = new RouteRequirements(
                request.requirements().requiredRange(),
                request.requirements().requiredCapacity()
        );

        Route route = routeService.createRoute(
                request.routeName(),
                request.originIataCode(),
                request.destinationIataCode(),
                request.estimatedFlightTimeHours(),
                request.type() == null ? null : RouteType.valueOf(request.type()),
                requirements
        );

        return RouteResponse.from(route);
    }

    @GetMapping("/{routeId}")
    public RouteResponse getRouteDetails(@PathVariable Long routeId) {
        return RouteResponse.from(routeService.getRouteDetails(routeId));
    }

    @GetMapping("/{routeId}/history")
    public RouteHistoryResponse getRouteHistory(@PathVariable Long routeId) {
        return RouteHistoryResponse.from(routeService.getRouteHistory(routeId));
    }

    @GetMapping("/from/{airportIataCode}")
    public List<RouteResponse> getRoutesFromAirport(@PathVariable String airportIataCode) {
        return routeService.getRoutesFromAirport(airportIataCode)
                .stream()
                .map(RouteResponse::from)
                .toList();
    }

    @GetMapping
    public List<RouteResponse> searchRoutes(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        return routeService.searchRoutes(origin, destination)
                .stream()
                .map(RouteResponse::from)
                .toList();
    }

    @PatchMapping("/{routeId}")
    public RouteResponse updateRoute(@PathVariable Long routeId,
                                     @Valid @RequestBody UpdateRouteRequest request) {
        Route route = routeService.updateRoute(
                routeId,
                request.destinationIataCode(),
                request.estimatedFlightTimeHours(),
                request.status() == null ? null : RouteStatus.valueOf(request.status())
        );

        return RouteResponse.from(route);
    }

    record AddRouteRequest(String routeName, String originIataCode,
                           String destinationIataCode, Double estimatedFlightTimeHours,
                           String type, RouteRequirementsRequest requirements) {}

    record RouteRequirementsRequest(Double requiredRange, Integer requiredCapacity) {}

    record UpdateRouteRequest(String destinationIataCode,
                              Double estimatedFlightTimeHours, String status) {}

    public record RouteResponse(Long routeId, String routeName, RouteStatus status,
                                RouteType type, String originIataCode,
                                String destinationIataCode, Double estimatedFlightTimeHours,
                                RouteRequirementsResponse requirements,
                                RouteHistoryResponse history) {
        static RouteResponse from(Route route) {
            return new RouteResponse(
                    route.getRouteId(),
                    route.getRouteName(),
                    route.getStatus(),
                    route.getType(),
                    route.getOriginAirport().getIataCode(),
                    route.getDestinationAirport().getIataCode(),
                    route.getEstimatedFlightTimeHours(),
                    RouteRequirementsResponse.from(route.getRouteRequirements()),
                    RouteHistoryResponse.from(route.getRouteHistory())
            );
        }
    }

    public record RouteRequirementsResponse(Double requiredRange, Integer requiredCapacity) {
        static RouteRequirementsResponse from(RouteRequirements requirements) {
            if (requirements == null) {
                return null;
            }
            return new RouteRequirementsResponse(
                    requirements.getRequiredRange(),
                    requirements.getRequiredCapacity()
            );
        }
    }

    public record RouteHistoryResponse(LocalDate routeBegin,
                                       LocalDate routeFinish,
                                       Integer routeUsage) {
        static RouteHistoryResponse from(RouteHistory history) {
            if (history == null) {
                return null;
            }
            return new RouteHistoryResponse(
                    history.getRouteBegin(),
                    history.getRouteFinish(),
                    history.getRouteUsage()
            );
        }
    }
}
