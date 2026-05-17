package com.example.AISafePSOFT_26.Aircraft;

import com.example.AISafePSOFT_26.Aircraft.application.AddAircraftUseCase;
import com.example.AISafePSOFT_26.Aircraft.application.AircraftSearchService;
import com.example.AISafePSOFT_26.Aircraft.domain.*;
import com.example.AISafePSOFT_26.Aircraft.application.AircraftLifeCycleUpdaterService;
import com.example.AISafePSOFT_26.AircraftCatalog.application.AircraftModelSearchService;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hangar")
public class HangarController {
    private final AddAircraftUseCase addAircraftUseCase;
    private final AircraftModelSearchService aircraftModelSearchService;
    private final AircraftLifeCycleUpdaterService aircraftLifeCycleUpdaterService;
    private final AircraftSearchService aircraftSearchService;

    public HangarController(AddAircraftUseCase addAircraftUseCase, AircraftModelSearchService aircraftModelSearchService, AircraftLifeCycleUpdaterService aircraftLifeCycleUpdaterService, AircraftSearchService aircraftSearchService) {
        this.addAircraftUseCase = addAircraftUseCase;
        this.aircraftModelSearchService = aircraftModelSearchService;
        this.aircraftLifeCycleUpdaterService = aircraftLifeCycleUpdaterService;
        this.aircraftSearchService = aircraftSearchService;
    }

    /**
     * Adds a new aircraft model to the catalog.
     */
    @PostMapping("/aircraft")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAircraft(@Valid @RequestBody AddAircraftRequest request) {
        AircraftModel model = aircraftModelSearchService
                .spotAircraftInCatalog(request.modelName())
                .orElseThrow(() ->
                        new DomainException("Model does not exist in catalog"));
        Aircraft aircraft = new Aircraft(
                request.registrationNumber(),
                model,
                request.manufacturingDate(),
                request.totalOperationalHours(),
                request.totalFlightHours()
        );
        aircraftLifeCycleUpdaterService.changeAvailability(aircraft,
                AircraftAvailability.valueOf(request.availability()));
        addAircraftUseCase.execute(aircraft);
    }
    /**
     * Request body for POST /hangar/aircraft
     */
    record AddAircraftRequest(String registrationNumber, String modelName, LocalDate manufacturingDate,
                              Double totalOperationalHours, Double totalFlightHours, String availability) {}

    /**
     * Selects an aircraft with a specific Id.
     */
    @GetMapping("/aircraft/{registrationNumber}")
    public AircraftResponse getAircraft(@PathVariable String registrationNumber) {
        Aircraft aircraft = aircraftSearchService
                .spotAircraftInHangar(registrationNumber)
                .orElseThrow(() ->
                        new DomainException(
                                "Aircraft does not exist in hangar"
                        )
                );
        return AircraftResponse.from(aircraft);
    }

    /**
    *Request body for PATCH /hangar/aircraft{id}
    */
    record  PatchAircraftAvailabilityRequest(String registrationNumber,String availability) {}

    /**
    * Changes aircraft availability to the requested valid value.
    */
    @PatchMapping("/aircraft/{registrationNumber}")
    public void changeAircraft(
            @PathVariable String registrationNumber,
            @Valid @RequestBody PatchAircraftAvailabilityRequest request) {
        if (!registrationNumber.equals(request.registrationNumber())) {
            throw new DomainException("Non matching info");
        }

        Aircraft aircraft = aircraftSearchService
                .spotAircraftInHangar(request.registrationNumber())
                .orElseThrow(() ->
                        new DomainException(
                                "Aircraft does not exist in hangar"
                        )
                );
        aircraftLifeCycleUpdaterService.changeAvailability(aircraft,AircraftAvailability.valueOf(request.availability()));
    }

    @GetMapping("/aircraft")
    public List<AircraftResponse> getAircrafts(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String status,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate manufacturingDate) {
        return aircraftSearchService
                .findAircraftsMatchingFilter(
                        model,
                        status,
                        manufacturingDate
                )
                .stream()
                .map(AircraftResponse::from)
                .toList();
    }

    /**
    * Response body representing an aircraft.
    */
    public record AircraftResponse(String registrationNumber, String modelName, AircraftAvailability status,
            LocalDate manufacturingDate, Double totalOperationalHours, Double totalFlightHours,
            Double meanRange, List<String> features) {

        public static AircraftResponse from(Aircraft aircraft) {
            return new AircraftResponse(aircraft.getRegistrationNumber(),
                    aircraft.getModel().getModelName(), aircraft.getStatus(),
                    aircraft.getManufacturingDate(), aircraft.getTotalOperationalHours(),
                    aircraft.getTotalFlightHours(), aircraft.getMeanRange(),
                    aircraft.getFeatures()
            );
        }
    }
}