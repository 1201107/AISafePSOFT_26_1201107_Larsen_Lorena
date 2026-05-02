package com.example.AISafePSOFT_26.Maintenance.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "company_maintenance_records")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID recordId;

    private String aircraftRegistrationNumber;

    private Long maintenanceTemplateId;

    private String description;

    @ElementCollection
    private List<UUID> technicianIds = new ArrayList<>();

    private UUID supervisorId;

    private LocalDate startDate;
    private LocalDate endDate;

    @ElementCollection
    private List<UsedPart> usedParts = new ArrayList<>();

    private Double duration;

    @Embedded
    private DoneList doneList;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    public void updateDuration(Double realDuration) {
        this.duration = realDuration;
    }

    public void markAsCompleted(String notes, Map<String, Boolean> tasksDone) {
        this.status = MaintenanceStatus.COMPLETED;
        this.doneList = new DoneList(notes, tasksDone);
    }
}