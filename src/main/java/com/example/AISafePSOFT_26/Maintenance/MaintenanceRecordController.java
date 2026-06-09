package com.example.AISafePSOFT_26.Maintenance;

import com.example.AISafePSOFT_26.Aircraft.application.AircraftSearchService;
import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Maintenance.aplication.AddMaintenanceRecordUseCase;
import com.example.AISafePSOFT_26.Maintenance.aplication.AddMaintenanceTemplateUseCase;
import com.example.AISafePSOFT_26.Maintenance.aplication.MaintenanceAlertService;
import com.example.AISafePSOFT_26.Maintenance.aplication.MaintenanceRecordProgressService;
import com.example.AISafePSOFT_26.Maintenance.aplication.MaintenanceReportService;
import com.example.AISafePSOFT_26.Maintenance.aplication.MaintenanceTemplateSearchService;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceAttribute;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceRecord;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceStatus;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceTemplate;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceType;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import com.example.AISafePSOFT_26.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/maintenance")
public class MaintenanceRecordController {

    private final AddMaintenanceTemplateUseCase addMaintenanceTemplateUseCase;
    private final AddMaintenanceRecordUseCase addMaintenanceRecordUseCase;
    private final AircraftSearchService aircraftSearchService;
    private final MaintenanceTemplateSearchService maintenanceTemplateSearchService;
    private final MaintenanceRecordProgressService maintenanceRecordProgressService;
    private final MaintenanceReportService maintenanceReportService;
    private final MaintenanceAlertService maintenanceAlertService;

    public MaintenanceRecordController(AddMaintenanceTemplateUseCase addMaintenanceTemplateUseCase,
                                       AddMaintenanceRecordUseCase addMaintenanceRecordUseCase,
                                       AircraftSearchService aircraftSearchService,
                                       MaintenanceTemplateSearchService maintenanceTemplateSearchService,
                                       MaintenanceRecordProgressService maintenanceRecordProgressService,
                                       MaintenanceReportService maintenanceReportService,
                                       MaintenanceAlertService maintenanceAlertService) {
        this.addMaintenanceTemplateUseCase = addMaintenanceTemplateUseCase;
        this.addMaintenanceRecordUseCase = addMaintenanceRecordUseCase;
        this.aircraftSearchService = aircraftSearchService;
        this.maintenanceTemplateSearchService = maintenanceTemplateSearchService;
        this.maintenanceRecordProgressService = maintenanceRecordProgressService;
        this.maintenanceReportService = maintenanceReportService;
        this.maintenanceAlertService = maintenanceAlertService;
    }

    // WP3 — criar template
    @PostMapping("/templates")
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceTemplateResponse addTemplate(@Valid @RequestBody AddTemplateRequest request) {
        MaintenanceTemplate template = addMaintenanceTemplateUseCase.execute(
                request.name(),
                request.expectedDuration(),
                request.templateChecklist(),
                MaintenanceType.valueOf(request.operation()),
                MaintenanceAttribute.valueOf(request.attribute())
        );
        return MaintenanceTemplateResponse.from(template);
    }

    // US117 — obter template por id
    @GetMapping("/templates/{id}")
    public MaintenanceTemplateResponse getTemplate(@PathVariable Long id) {
        MaintenanceTemplate template = maintenanceTemplateSearchService
                .findByTemplateId(id)
                .orElseThrow(() -> new DomainException("Maintenance Template does not exist"));
        return MaintenanceTemplateResponse.from(template);
    }

    // US117 — listar templates
    @GetMapping("/templates")
    public List<MaintenanceTemplateResponse> listTemplates() {
        return maintenanceTemplateSearchService.findAll()
                .stream()
                .map(MaintenanceTemplateResponse::from)
                .toList();
    }

    // WP3 — criar registo curto
    @PostMapping("/record")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRecord(@Valid @RequestBody AddRecordShortRequest request) {
        Aircraft aircraft = aircraftSearchService
                .spotAircraftInHangar(request.registrationNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft does not exist in hangar"));

        MaintenanceTemplate maintenanceTemplate = maintenanceTemplateSearchService
                .findByTemplateId(request.templateId())
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance Template does not exist"));

        addMaintenanceRecordUseCase.executeShort(
                aircraft,
                maintenanceTemplate,
                request.durationHours(),
                request.description(),
                request.startDate()
        );
    }

    // US119 — obter registo por id
    @GetMapping("/records/{id}")
    public MaintenanceRecordResponse getRecord(@PathVariable Long id) {
        MaintenanceRecord record = maintenanceRecordProgressService
                .findById(id)
                .orElseThrow(() -> new DomainException("Maintenance Record does not exist"));
        return MaintenanceRecordResponse.from(record);
    }

    // US119 — avançar estado do registo
    @PatchMapping("/records/{id}/status")
    public MaintenanceRecordResponse advanceStatus(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateRecordStatusRequest request) {
        MaintenanceRecord record = maintenanceRecordProgressService.advanceStatus(
                id,
                MaintenanceStatus.valueOf(request.status())
        );
        return MaintenanceRecordResponse.from(record);
    }

    // US221 — tempo médio de manutenção por tipo de aeronave
    @GetMapping("/turnaround-average")
    public List<TurnaroundAverageResponse> turnaroundAverage() {
        return maintenanceReportService.averageTurnaroundTimePerAircraftType()
                .stream()
                .map(t -> new TurnaroundAverageResponse(t.aircraftType(), t.averageDays()))
                .toList();
    }

    // US219 - atividades de manutencao em curso na frota
    @GetMapping("/ongoing")
    public List<MaintenanceRecordResponse> ongoingMaintenanceActivities() {
        return maintenanceReportService.ongoingMaintenanceActivities()
                .stream()
                .map(MaintenanceRecordResponse::from)
                .toList();
    }

    // US220 - custos de manutencao por aeronave ou modelo
    @GetMapping("/reports/costs")
    public List<MaintenanceCostReportResponse> maintenanceCosts(
            @RequestParam(defaultValue = "aircraft") String groupBy) {
        return maintenanceReportService.maintenanceCosts(groupBy)
                .stream()
                .map(r -> new MaintenanceCostReportResponse(r.group(), r.totalCost()))
                .toList();
    }

    // US222 — alertas de manutenção por horas de voo ou dias de calendário
    @GetMapping("/alerts")
    public List<MaintenanceAlertResponse> maintenanceAlerts(
            @RequestParam(defaultValue = "180") int calendarDaysThreshold,
            @RequestParam(defaultValue = "500") double flightHoursThreshold) {
        return maintenanceAlertService.findAircraftDueForMaintenance(calendarDaysThreshold, flightHoursThreshold)
                .stream()
                .map(a -> new MaintenanceAlertResponse(
                        a.registrationNumber(),
                        a.aircraftModel(),
                        a.totalFlightHours(),
                        a.lastMaintenanceDate(),
                        a.daysSinceLastMaintenance(),
                        a.alertReason()
                ))
                .toList();
    }

    // DTOs
    record AddRecordShortRequest(@NotBlank String registrationNumber,
                                 @NotNull Long templateId,
                                 @NotNull Double durationHours,
                                 @NotBlank String description,
                                 @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {}

    record AddTemplateRequest(@NotBlank String name,
                              @NotNull Double expectedDuration,
                              @NotNull Map<String, Boolean> templateChecklist,
                              @NotBlank String operation,
                              @NotBlank String attribute) {}

    record UpdateRecordStatusRequest(@NotBlank String status) {}

    // Responses
    record MaintenanceTemplateResponse(Long templateId, String name,
                                       Double expectedDuration,
                                       Map<String, Boolean> templateChecklist,
                                       MaintenanceType operation,
                                       MaintenanceAttribute attribute) {
        static MaintenanceTemplateResponse from(MaintenanceTemplate template) {
            return new MaintenanceTemplateResponse(
                    template.getTemplateId(),
                    template.getName(),
                    template.getExpectedDuration(),
                    template.getTemplateChecklist(),
                    template.getOperation(),
                    template.getAttribute()
            );
        }
    }

    record MaintenanceRecordResponse(Long id,
                                     String aircraftRegistration,
                                     Long templateId,
                                     String templateName,
                                     Double durationHours,
                                     String description,
                                     LocalDate startDate,
                                     MaintenanceStatus status) {
        static MaintenanceRecordResponse from(MaintenanceRecord r) {
            return new MaintenanceRecordResponse(
                    r.getRecordId(),
                    r.getAircraft().getRegistrationNumber(),
                    r.getMaintenanceTemplate().getTemplateId(),
                    r.getMaintenanceTemplate().getName(),
                    r.getDurationHours(),
                    r.getDescription(),
                    r.getStartDate(),
                    r.getStatus()
            );
        }
    }

    record TurnaroundAverageResponse(String aircraftType, Double averageDays) {}

    record MaintenanceCostReportResponse(String group, Double totalCost) {}

    record MaintenanceAlertResponse(String registrationNumber,
                                    String aircraftModel,
                                    Double totalFlightHours,
                                    LocalDate lastMaintenanceDate,
                                    Long daysSinceLastMaintenance,
                                    String alertReason) {}
}
