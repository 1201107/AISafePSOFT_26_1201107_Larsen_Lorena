package com.example.AISafePSOFT_26.Maintenance.aplication;

import com.example.AISafePSOFT_26.Maintenance.infrastructure.MaintenanceRecordRepository;

public class MaintenanceRecordLifeCycleUpdater {
    private MaintenanceRecordRepository maintenanceRecordRepository;

    public MaintenanceRecordLifeCycleUpdater(MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }
}
