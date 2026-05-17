package com.example.AISafePSOFT_26.Aircraft;

import com.example.AISafePSOFT_26.Aircraft.application.AddAircraftUseCase;
import com.example.AISafePSOFT_26.Aircraft.application.AircraftSearchService;
import com.example.AISafePSOFT_26.Aircraft.domain.*;
import com.example.AISafePSOFT_26.Aircraft.application.AircraftLifeCycleUpdaterService;
import com.example.AISafePSOFT_26.AircraftCatalog.application.AircraftModelSearchService;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hangar")
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
    @GetMapping("/aircraft/{id}")
    public AircraftResponse getAircraft(@PathVariable String id) {
        Aircraft aircraft = aircraftSearchService
                .spotAircraftInHangar(id)
                .orElseThrow(() ->
                        new DomainException(
                                "Aircraft does not exist in hangar"
                        )
                );
        return AircraftResponse.from(aircraft);
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