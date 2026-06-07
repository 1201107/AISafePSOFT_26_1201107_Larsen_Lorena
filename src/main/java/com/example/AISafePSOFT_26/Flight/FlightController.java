package com.example.AISafePSOFT_26.Flight;

import com.example.AISafePSOFT_26.Aircraft.application.AircraftSearchService;
import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Flight.domain.Flight;
import com.example.AISafePSOFT_26.Flight.domain.FlightStatus;
import com.example.AISafePSOFT_26.Route.application.RouteService;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final RouteService routeService;
    private final AircraftSearchService aircraftSearchService;

    public FlightController(RouteService routeService,
            AircraftSearchService aircraftSearchService) {
        this.routeService = routeService;
        this.aircraftSearchService = aircraftSearchService;
    }

    @PostMapping("/scheduled")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse scheduleFlight(@Valid @RequestBody ScheduleFlightRequest request) {
        Aircraft aircraft = aircraftSearchService
                .spotAircraftInHangar(request.aircraftRegistrationNumber())
                .orElseThrow(() -> new DomainException("Aircraft does not exist"));

        return FlightResponse.from(routeService.scheduleFlight(request.routeId(),
                aircraft, request.scheduledDeparture(), request.scheduledArrival()));
    }

    @GetMapping("/aircraft/{registrationNumber}")
    public List<FlightResponse> getScheduledFlightsForAircraft(
            @PathVariable String registrationNumber) {
        return routeService.getScheduledFlightsForAircraft(registrationNumber)
                .stream()
                .map(FlightResponse::from)
                .toList();
    }

    record ScheduleFlightRequest(
            @NotNull Long routeId,
            @NotBlank String aircraftRegistrationNumber,
            @NotNull LocalDateTime scheduledDeparture,
            @NotNull LocalDateTime scheduledArrival) {
    }

    public record FlightResponse(Long flightId, Long routeId,
            String aircraftRegistrationNumber, FlightStatus status,
            LocalDateTime scheduledDeparture, LocalDateTime scheduledArrival) {

        public static FlightResponse from(Flight flight) {
            return new FlightResponse(flight.getFlightId(),
                    flight.getRoute().getRouteId(),
                    flight.getAircraft().getRegistrationNumber(),
                    flight.getFlightStatus(),
                    flight.getScheduledDeparture(),
                    flight.getScheduledArrival());
        }
    }
}
