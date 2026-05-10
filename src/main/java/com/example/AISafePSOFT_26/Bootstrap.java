package com.example.AISafePSOFT_26;

import com.example.AISafePSOFT_26.Aircraft.domain.*;
import com.example.AISafePSOFT_26.Aircraft.infrastructure.AircraftRepository;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftSpecs;
import com.example.AISafePSOFT_26.AircraftCatalog.infrastructure.AircraftModelRepository;
import com.example.AISafePSOFT_26.Airport.domain.*;
import com.example.AISafePSOFT_26.Airport.infrastructure.AirportRepository;
import com.example.AISafePSOFT_26.Flight.domain.Flight;
import com.example.AISafePSOFT_26.Flight.infrastructure.FlightRepository;
import com.example.AISafePSOFT_26.Maintenance.domain.*;
import com.example.AISafePSOFT_26.Maintenance.infrastructure.MaintenanceRecordRepository;
import com.example.AISafePSOFT_26.Maintenance.infrastructure.MaintenanceTemplateRepository;
import com.example.AISafePSOFT_26.Route.domain.*;
import com.example.AISafePSOFT_26.Route.infrastructure.RouteRepository;
import com.example.AISafePSOFT_26.Users.domain.Collaborator;
import com.example.AISafePSOFT_26.Users.domain.Role;
import com.example.AISafePSOFT_26.Users.infrastructure.CollaboratorRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile("dev")
public class Bootstrap implements ApplicationRunner {

    private final CollaboratorRepository collaboratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AircraftModelRepository aircraftModelRepository;
    private final AirportRepository airportRepository;
    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;
    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final MaintenanceTemplateRepository maintenanceTemplateRepository;

    public Bootstrap(
            CollaboratorRepository collaboratorRepository,
            PasswordEncoder passwordEncoder,
            AircraftModelRepository aircraftModelRepository,
            AirportRepository airportRepository,
            AircraftRepository aircraftRepository,
            FlightRepository flightRepository,
            RouteRepository routeRepository,
            MaintenanceRecordRepository maintenanceRecordRepository,
            MaintenanceTemplateRepository maintenanceTemplateRepository) {
        this.collaboratorRepository = collaboratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.aircraftModelRepository = aircraftModelRepository;
        this.airportRepository = airportRepository;
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
        this.routeRepository = routeRepository;
        this.maintenanceRecordRepository = maintenanceRecordRepository;
        this.maintenanceTemplateRepository = maintenanceTemplateRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        if (collaboratorRepository.count() == 0) {
            seedCollaborators();
        }

        if (aircraftModelRepository.count() == 0) {
            seedAircraftModels();
        }

        if (airportRepository.count() == 0) {
            seedAirports();
        }

        if (aircraftRepository.count() == 0) {
            seedAircraft();
        }

        if (routeRepository.count() == 0) {
            seedRoutes();
        }

        if (flightRepository.count() == 0) {
            seedFlights();
        }

        if (maintenanceRecordRepository.count() == 0) {
            seedMaintenance();
        }
    }

    private void seedCollaborators() {
        collaboratorRepository.saveAll(List.of(
                new Collaborator("admin", "admin@email.com",
                        passwordEncoder.encode("admin123"), Set.of(Role.ADMIN)),

                new Collaborator("atcc", "atcc@email.com",
                        passwordEncoder.encode("atcc123"), Set.of(Role.ATCC)),

                new Collaborator("backoffice", "backoffice@email.com",
                        passwordEncoder.encode("backoffice123"), Set.of(Role.BACKOFFICE)),

                new Collaborator("technician", "technician@email.com",
                        passwordEncoder.encode("technician123"), Set.of(Role.MAINTENANCE_TECHNICIAN)),

                new Collaborator("supervisor", "supervisor@email.com",
                        passwordEncoder.encode("supervisor123"), Set.of(Role.MAINTENANCE_SUPERVISOR))
        ));
    }

    private void seedAircraftModels() {
        aircraftModelRepository.saveAll(List.of(
                new AircraftModel("Boeing 737-800", "Boeing",
                        new AircraftSpecs(26020.0, 5436.0, 842.0,120),
                        List.of("b737-front.jpg", "b737-side.jpg")),

                new AircraftModel("Airbus A320neo", "Airbus",
                        new AircraftSpecs(24210.0, 6300.0, 828.0,120),
                        List.of("a320-1.jpg", "a320-2.jpg")),

                new AircraftModel("Boeing 787-9 Dreamliner", "Boeing",
                        new AircraftSpecs(126000.0, 14140.0, 913.0,120),
                        List.of("b787-1.jpg")),

                new AircraftModel("Embraer E195-E2", "Embraer",
                        new AircraftSpecs(13200.0, 4815.0, 870.0,120),
                        List.of("e195.jpg"))
        ));
    }

    private void seedAirports() {
        airportRepository.save(new Airport(
                "OPO",
                "International",
                "Francisco Sá Carneiro Airport",
                AirportStatus.OPERATIONAL,
                new AirportLocation(
                        "Porto",
                        -8.68139,
                        41.2421,
                        "Porto District",
                        "Europe/Lisbon",
                        "Portugal"
                ),
                new Facilities(
                        1,
                        List.of(
                                "Cargo Terminal",
                                "Restaurants",
                                "Duty Free",
                                "Free WiFi",
                                "Parking"
                        ),
                        35
                ),
                313.0,
                List.of(
                        new Runway(
                                null,
                                "17/35",
                                3480.0,
                                "Asphalt",
                                RunwayStatus.IN_USE
                        )
                ),
                List.of(
                        new Certification(
                                "ICAO Annex 14 Compliance",
                                "SAFETY",
                                LocalDate.of(2023, 1, 1),
                                LocalDate.of(2027, 1, 1)
                        )
                ),
                List.of(
                        new Contact(ContactType.PHONE, "+351 22 943 2400"),
                        new Contact(ContactType.EMAIL, "info@ana.pt")
                ),
                List.of(
                        "opo_terminal.jpg",
                        "opo_runway.jpg"
                )
        ));

        airportRepository.save(new Airport(
                "LIS",
                "International",
                "Humberto Delgado Airport",
                AirportStatus.OPERATIONAL,
                new AirportLocation(
                        "Lisbon",
                        -9.13592,
                        38.7813,
                        "Lisbon Region",
                        "Europe/Lisbon",
                        "Portugal"
                ),
                new Facilities(
                        2,
                        List.of(
                                "VIP Lounge",
                                "Medical Center",
                                "Duty Free",
                                "Metro Access",
                                "WiFi"
                        ),
                        47
                ),
                0.0,
                List.of(
                        new Runway(
                                null,
                                "03/21",
                                3805.0,
                                "Asphalt",
                                RunwayStatus.OPEN
                        ),
                        new Runway(
                                null,
                                "17/35",
                                2400.0,
                                "Asphalt",
                                RunwayStatus.UNDER_MAINTENANCE
                        )
                ),
                List.of(
                        new Certification(
                                "ICAO Safety Certification",
                                "SAFETY",
                                LocalDate.of(2024, 1, 1),
                                LocalDate.of(2028, 1, 1)
                        ),
                        new Certification(
                                "EASA Aerodrome License",
                                "REGULATORY",
                                LocalDate.of(2023, 6, 1),
                                LocalDate.of(2026, 6, 1)
                        )
                ),
                List.of(
                        new Contact(ContactType.PHONE, "+351 21 841 3500"),
                        new Contact(ContactType.EMAIL, "lisbon.airport@ana.pt")
                ),
                List.of(
                        "lis_terminal_1.jpg",
                        "lis_runway_night.jpg"
                )
        ));
    }

    private void seedAircraft() {
        AircraftModel model = aircraftModelRepository
                .findByModelName("Boeing 737-800")
                .orElseThrow();

        Aircraft aircraft = new Aircraft(
                "CS-TUA",
                model,
                LocalDate.of(2018, 5, 10),
                12500.0,
                9800.0
        );

        aircraft.configureSeating(new SeatingPack(189, "3-3"));
        aircraft.addFeature("WiFi");
        aircraft.addCertification(new AircraftCertification(
                "EASA Safety Compliance Approval",
                "SAFETY",
                LocalDate.of(2023, 6, 1),
                LocalDate.of(2026, 6, 1)
        ));
        aircraft.addCertification(new AircraftCertification(
                "ICAO Operational Certification",
                "OPERATIONAL",
                LocalDate.of(2022, 3, 15),
                LocalDate.of(2025, 3, 15)
        ));
        aircraft.installComponent(new InstalledComponent(
                "CFM56-7B-ENG-001",
                "CFM56-7B",
                "ACTIVE",
                aircraft
        ));
        aircraftRepository.save(aircraft);
    }

    private void seedRoutes() {
        Airport origin = airportRepository.findByIataCode("OPO")
                .orElseThrow();

        Airport dest = airportRepository.findByIataCode("LIS")
                .orElseThrow();

        Route route = new Route(
                new RouteRequirements(2000.0, 128),
                RouteStatus.ACTIVE,
                RouteType.SCALED,
                new RouteHistory(LocalDate.of(2018, 5, 10), 0),
                24.0,
                origin,
                dest,"Pati&Patatá"
        );

        routeRepository.save(route);
    }

    private void seedFlights() {
        Aircraft aircraft = aircraftRepository.findByRegistrationNumber("CS-TUA")
                .orElseThrow();

        Route route = routeRepository.findAll().get(0);

        Flight flight = new Flight(
                route,
                aircraft,
                LocalDateTime.of(2026, 5, 10, 12, 0),
                LocalDateTime.of(2026, 5, 10, 18, 0)
        );

        flightRepository.save(flight);
    }

    private void seedMaintenance() {
        Aircraft aircraft = aircraftRepository.findByRegistrationNumber("CS-TUA")
                .orElseThrow();

        Collaborator supervisor = collaboratorRepository.findByUsername("supervisor")
                .orElseThrow();

        Collaborator tech = collaboratorRepository.findByUsername("technician")
                .orElseThrow();

        MaintenanceTemplate template = new MaintenanceTemplate(
                "template1",
                20.0,
                Map.of("Check oil", true, "Inspect motor", true),
                MaintenanceType.INSPECTION,
                MaintenanceAttribute.ENGINE
        );
        maintenanceTemplateRepository.save(template);
        List<UsedPart> parts = List.of(
                new UsedPart("ENG-001", 2, 1500.0)
        );

        MaintenanceRecord record = new MaintenanceRecord(
                parts,
                24.0,
                LocalDate.now(),
                supervisor,
                "Scheduled",
                List.of(tech),
                template,
                aircraft
        );
        record.updateDoneList(new DoneList(
                "Engine inspection completed. No abnormal oil leaks detected. All vibration readings within acceptable limits. Aircraft cleared for return to service.",
                Map.of(
                        "Check oil levels", true,
                        "Inspect engine turbine blades", true,
                        "Verify hydraulic pressure systems", true,
                        "Run engine ground test", true,
                        "Inspect fuel lines for leakage", true,
                        "Check avionics diagnostic logs", true
                )
        ));
        maintenanceRecordRepository.save(record);
    }
}