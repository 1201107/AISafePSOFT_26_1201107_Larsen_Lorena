package com.example.AISafePSOFT_26.Aircraft.application;

import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Aircraft.infrastructure.AircraftRepository;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.AircraftCatalog.infrastructure.AircraftModelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AircraftSearchService {
    private final AircraftRepository aircraftRepository;

    public AircraftSearchService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public Optional<Aircraft> spotAircraftInHangar(String id) {
        return aircraftRepository.findById(id);
    }
}