package com.example.AISafePSOFT_26.Aircraft.infrastructure;

import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {
    Optional<Aircraft> findByRegistrationNumber(String registrationNumber);

}