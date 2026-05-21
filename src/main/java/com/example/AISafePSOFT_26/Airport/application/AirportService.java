package com.example.AISafePSOFT_26.Airport.application;

import com.example.AISafePSOFT_26.Airport.domain.Airport;
import com.example.AISafePSOFT_26.Airport.domain.AirportLocation;
import com.example.AISafePSOFT_26.Airport.domain.AirportStatus;
import com.example.AISafePSOFT_26.Airport.domain.Certification;
import com.example.AISafePSOFT_26.Airport.domain.Contact;
import com.example.AISafePSOFT_26.Airport.domain.Facilities;
import com.example.AISafePSOFT_26.Airport.domain.Runway;
import com.example.AISafePSOFT_26.Airport.infrastructure.AirportRepository;
import com.example.AISafePSOFT_26.UseCase;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@UseCase
@Transactional
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public void execute(Airport airport) {
        validateIataCode(airport.getIataCode());
        if (airportRepository.findByIataCode(airport.getIataCode()).isPresent()) {
            throw new DomainException("Airport already exists");
        }
        airportRepository.save(airport);
    }

    public void execute(String iataCode, String airportType, String name,
                        AirportStatus status, AirportLocation airportLocation,
                        Facilities facilities, Double routeDistance,
                        List<Runway> airportRunways, List<Certification> certifications,
                        List<Contact> contacts, List<String> airportPhotos) {
        execute(new Airport(iataCode, airportType, name, status, airportLocation,
                facilities, routeDistance, airportRunways, certifications,
                contacts, airportPhotos));
    }

    public Optional<Airport> getAirportByIata(String iataCode) {
        return airportRepository.findByIataCode(iataCode);
    }

    public List<Airport> searchAirports(String city, String country, String name) {
        Specification<Airport> spec = (root, query, cb) -> cb.conjunction();

        if (city != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("airportLocation").get("city"), city));
        }

        if (country != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("airportLocation").get("country"), country));
        }

        if (name != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("name"), name));
        }

        return airportRepository.findAll(spec);
    }

    public Certification addCertification(String iataCode, String name,
                                          String category, LocalDate startsAt, LocalDate expiresAt) {
        Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new DomainException("Airport does not exist"));

        Certification certification =
                new Certification(name, category, startsAt, expiresAt);
        airport.addCertification(certification);
        airportRepository.save(airport);
        return certification;
    }

    public Airport updateStatus(String iataCode, AirportStatus status) {
        Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new DomainException("Airport does not exist"));
        airport.changeStatus(status);
        return airportRepository.save(airport);
    }

    private void validateIataCode(String iataCode) {
        if (iataCode == null || !iataCode.matches("[A-Z]{3}")) {
            throw new DomainException("IATA code must have 3 uppercase letters");
        }
    }
}