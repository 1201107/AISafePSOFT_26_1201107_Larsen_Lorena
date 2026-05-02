package com.example.AISafePSOFT_26.Aircraft.infrastructure;

import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {
}