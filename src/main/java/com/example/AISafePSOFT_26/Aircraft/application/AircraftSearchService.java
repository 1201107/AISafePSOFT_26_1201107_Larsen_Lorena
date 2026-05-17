package com.example.AISafePSOFT_26.Aircraft.application;

import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Aircraft.domain.AircraftAvailability;
import com.example.AISafePSOFT_26.Aircraft.infrastructure.AircraftRepository;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.AircraftCatalog.infrastructure.AircraftModelRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public List<Aircraft> findAircraftsMatchingFilter(String model,
            String status,LocalDate manufacturingDate) {

        Specification<Aircraft> spec =
                (root, query, cb) -> cb.conjunction();
        if (model != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("model").get("modelName"), model));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(
                            root.get("status"),
                            AircraftAvailability.valueOf(status.toUpperCase())
                    ));
        }

        if (manufacturingDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("manufacturingDate"), manufacturingDate));
        }

        return aircraftRepository.findAll(spec);
    }
}