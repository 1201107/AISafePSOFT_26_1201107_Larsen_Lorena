package com.example.AISafePSOFT_26.Maintenance;

import com.example.AISafePSOFT_26.Aircraft.application.AircraftSearchService;
import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Maintenance.aplication.AddMaintenanceRecordUseCase;
import com.example.AISafePSOFT_26.Maintenance.aplication.MaintenanceTemplateSearchService;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceTemplate;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceRecordController {
    private final AddMaintenanceRecordUseCase addMaintenanceRecordUseCase;
    private final AircraftSearchService aircraftSearchService;
    private final MaintenanceTemplateSearchService maintenanceTemplateSearchService;

    public MaintenanceRecordController(AddMaintenanceRecordUseCase addMaintenanceRecordUseCase, AircraftSearchService aircraftSearchService, MaintenanceTemplateSearchService maintenanceTemplateSearchService) {
        this.addMaintenanceRecordUseCase = addMaintenanceRecordUseCase;
        this.aircraftSearchService = aircraftSearchService;
        this.maintenanceTemplateSearchService = maintenanceTemplateSearchService;
    }

    /**
     * Adds a new maintenance record to the records.
     */
    @PostMapping("/record")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRecord(@Valid @RequestBody AddRecordShortRequest request) {
        Aircraft aircraft = aircraftSearchService
                .spotAircraftInHangar(request.registrationNumber())
                .orElseThrow(() ->
                        new DomainException(
                                "Aircraft does not exist in hangar"
                        )
        );

        MaintenanceTemplate maintenanceTemplate = maintenanceTemplateSearchService
                .findByTemplateId(request.templateId()).orElseThrow(() ->
                                new DomainException(
                                        "Maintenance Template does not exist"
                                ));
        addMaintenanceRecordUseCase.executeShort(aircraft,maintenanceTemplate,request.durationHours(),
                request.description(),request.startDate());
    }

    /**
     * Request body for POST /maintenance/record
     */
    record AddRecordShortRequest(@NotBlank String registrationNumber,@NotNull Long templateId,
            @NotNull Double durationHours, @NotBlank String description,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){}
}