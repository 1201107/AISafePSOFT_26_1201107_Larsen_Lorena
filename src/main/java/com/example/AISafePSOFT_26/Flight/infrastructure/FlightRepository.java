package com.example.AISafePSOFT_26.Flight.infrastructure;

import com.example.AISafePSOFT_26.Flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightId(Long flightId);
}
