package com.example.AISafePSOFT_26.Airport;

import com.example.AISafePSOFT_26.Airport.Application.AirportService;
import com.example.AISafePSOFT_26.Airport.domain.*;
import com.example.AISafePSOFT_26.exceptions.DomainException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAirport(@Valid @RequestBody AddAirportRequest request) {
        AirportLocation location = new AirportLocation(
                request.location().city(),
                request.location().longitude(),
                request.location().latitude(),
                request.location().region(),
                request.location().timezone(),
                request.location().country()
        );

        Facilities facilities = new Facilities(
                request.facilities().terminalCount(),
                request.facilities().services(),
                request.facilities().gateCount()
        );

        List<Runway> runways = request.runways().stream()
                .map(runway -> new Runway(
                        null,
                        runway.runwayName(),
                        runway.length(),
                        runway.orientation(),
                        RunwayStatus.valueOf(runway.status())
                ))
                .toList();

        List<Certification> certifications = request.certifications() == null
                ? List.of()
                : request.certifications().stream()
                .map(certification -> new Certification(
                        certification.name(),
                        certification.category(),
                        certification.startsAt(),
                        certification.expiresAt()
                ))
                .toList();

        List<Contact> contacts = request.contacts() == null
                ? List.of()
                : request.contacts().stream()
                .map(contact -> new Contact(
                        ContactType.valueOf(contact.type()),
                        contact.value()
                ))
                .toList();

        Airport airport = new Airport(
                request.iataCode(),
                request.airportType(),
                request.name(),
                AirportStatus.valueOf(request.status()),
                location,
                facilities,
                request.routeDistance(),
                runways,
                certifications,
                contacts,
                request.airportPhotos()
        );
        airportService.execute(airport);
    }

    @PostMapping("/{iataCode}/certifications")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificationResponse addCertification(
            @PathVariable String iataCode,
            @Valid @RequestBody AddCertificationRequest request) {
        Certification certification = airportService.addCertification(
                iataCode,
                request.name(),
                request.category(),
                request.startsAt(),
                request.expiresAt()
        );
        return CertificationResponse.from(certification);
    }

    @GetMapping("/{iataCode}")
    public AirportResponse getAirport(@PathVariable String iataCode) {
        Airport airport = airportService.getAirportByIata(iataCode)
                .orElseThrow(() -> new DomainException("Airport does not exist"));
        return AirportResponse.from(airport);
    }

    @GetMapping
    public List<AirportResponse> searchAirports(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String name) {
        return airportService.searchAirports(city, country, name)
                .stream()
                .map(AirportResponse::from)
                .toList();
    }

    @PatchMapping("/{iataCode}/status")
    public AirportResponse updateAirportStatus(
            @PathVariable String iataCode,
            @Valid @RequestBody UpdateAirportStatusRequest request) {
        Airport airport = airportService.updateStatus(
                iataCode,
                AirportStatus.valueOf(request.status())
        );
        return AirportResponse.from(airport);
    }

    record AddAirportRequest(String iataCode, String airportType, String name,
                             String status, AirportLocationRequest location,
                             FacilitiesRequest facilities, Double routeDistance,
                             List<RunwayRequest> runways,
                             List<CertificationRequest> certifications,
                             List<ContactRequest> contacts, List<String> airportPhotos) {}

    record AirportLocationRequest(String city, Double longitude,
                                  Double latitude, String region, String timezone, String country) {}

    record FacilitiesRequest(Integer terminalCount, List<String> services,
                             Integer gateCount) {}

    record RunwayRequest(String runwayName, Double length, String orientation,
                         String status) {}

    record CertificationRequest(String name, String category,
                                LocalDate startsAt, LocalDate expiresAt) {}

    record ContactRequest(String type, String value) {}

    record AddCertificationRequest(String name, String category,
                                   LocalDate startsAt, LocalDate expiresAt) {}

    record UpdateAirportStatusRequest(String status) {}

    public record AirportResponse(String iataCode, String airportType,
                                  String name, AirportStatus status, AirportLocationResponse location,
                                  FacilitiesResponse facilities, Double routeDistance,
                                  List<RunwayResponse> runways,
                                  List<CertificationResponse> certifications,
                                  List<ContactResponse> contacts, List<String> airportPhotos) {
        static AirportResponse from(Airport airport) {
            return new AirportResponse(
                    airport.getIataCode(),
                    airport.getAirportType(),
                    airport.getName(),
                    airport.getStatus(),
                    AirportLocationResponse.from(airport.getAirportLocation()),
                    FacilitiesResponse.from(airport.getFacilities()),
                    airport.getRouteDistance(),
                    airport.getAirportRunways().stream()
                            .map(RunwayResponse::from)
                            .toList(),
                    airport.getCertifications().stream()
                            .map(CertificationResponse::from)
                            .toList(),
                    airport.getContacts().stream()
                            .map(ContactResponse::from)
                            .toList(),
                    airport.getAirportPhotos()
            );
        }
    }

    record AirportLocationResponse(String city, Double longitude,
                                   Double latitude, String region, String timezone, String country) {
        static AirportLocationResponse from(AirportLocation location) {
            return new AirportLocationResponse(location.getCity(),
                    location.getLongitude(), location.getLatitude(),
                    location.getRegion(), location.getTimezone(),
                    location.getCountry());
        }
    }

    record FacilitiesResponse(Integer terminalCount, List<String> services,
                              Integer gateCount) {
        static FacilitiesResponse from(Facilities facilities) {
            return new FacilitiesResponse(facilities.getTerminalCount(),
                    facilities.getServices(), facilities.getGateCount());
        }
    }

    record RunwayResponse(String runwayName, Double length,
                          String orientation, RunwayStatus status) {
        static RunwayResponse from(Runway runway) {
            return new RunwayResponse(runway.getRunwayName(),
                    runway.getLength(), runway.getOrientation(),
                    runway.getStatus());
        }
    }

    record CertificationResponse(String name, String category,
                                 LocalDate startsAt, LocalDate expiresAt) {
        static CertificationResponse from(Certification certification) {
            return new CertificationResponse(certification.getName(),
                    certification.getCategory(), certification.getStartsAt(),
                    certification.getExpiresAt());
        }
    }

    record ContactResponse(ContactType type, String value) {
        static ContactResponse from(Contact contact) {
            return new ContactResponse(contact.getType(), contact.getValue());
        }
    }
}