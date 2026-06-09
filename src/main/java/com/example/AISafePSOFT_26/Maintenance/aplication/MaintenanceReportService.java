package com.example.AISafePSOFT_26.Maintenance.aplication;

import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceRecord;
import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceStatus;
import com.example.AISafePSOFT_26.Maintenance.infrastructure.MaintenanceRecordRepository;
import com.example.AISafePSOFT_26.UseCase;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UseCase
public class MaintenanceReportService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;

    public MaintenanceReportService(MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    public record TurnaroundAverage(String aircraftType, Double averageDays) {}

    public List<TurnaroundAverage> averageTurnaroundTimePerAircraftType() {
        List<MaintenanceRecord> completed = maintenanceRecordRepository.findByStatus(MaintenanceStatus.COMPLETED);

        Map<String, List<Long>> daysPerType = completed.stream()
                .filter(r -> r.getEndDate() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getAircraft().getModel().getModelName(),
                        Collectors.mapping(
                                r -> ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()),
                                Collectors.toList()
                        )
                ));

        return daysPerType.entrySet().stream()
                .map(e -> new TurnaroundAverage(
                        e.getKey(),
                        e.getValue().stream().mapToLong(l -> l).average().orElse(0.0)
                ))
                .sorted(Comparator.comparing(TurnaroundAverage::aircraftType))
                .toList();
    }
}