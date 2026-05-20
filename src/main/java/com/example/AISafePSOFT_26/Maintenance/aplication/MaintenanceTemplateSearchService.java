package com.example.AISafePSOFT_26.Maintenance.aplication;

import com.example.AISafePSOFT_26.Maintenance.domain.MaintenanceTemplate;
import com.example.AISafePSOFT_26.Maintenance.infrastructure.MaintenanceTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaintenanceTemplateSearchService {
    private final MaintenanceTemplateRepository maintenanceTemplateRepository;

    public MaintenanceTemplateSearchService(MaintenanceTemplateRepository maintenanceTemplateRepository) {
        this.maintenanceTemplateRepository = maintenanceTemplateRepository;
    }

    public Optional<MaintenanceTemplate> findByTemplateId(Long recordId){
        return maintenanceTemplateRepository.findByTemplateId(recordId);
    }
}