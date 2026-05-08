package com.example.AISafePSOFT_26;

import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftSpecs;
import com.example.AISafePSOFT_26.AircraftCatalog.infrastructure.AircraftModelRepository;
import com.example.AISafePSOFT_26.Airport.domain.*;
import com.example.AISafePSOFT_26.Airport.infrastructure.AirportRepository;
import com.example.AISafePSOFT_26.Users.domain.Collaborator;
import com.example.AISafePSOFT_26.Users.infrastructure.CollaboratorRepository;
import com.example.AISafePSOFT_26.Users.domain.Role;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@Profile("dev")
public class Bootstrap implements ApplicationRunner {

    private final CollaboratorRepository collaboratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AircraftModelRepository aircraftModelRepository;
    private final AirportRepository airportRepository;

    public Bootstrap(CollaboratorRepository collaboratorRepository,
                     PasswordEncoder passwordEncoder, AircraftModelRepository aircraftModelRepository, AirportRepository airportRepository) {
        this.collaboratorRepository = collaboratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.aircraftModelRepository = aircraftModelRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (collaboratorRepository.count() == 0) {
            collaboratorRepository.save(new Collaborator("admin","admin@email.com",passwordEncoder.encode("admin123"), Set.of(Role.ADMIN)));
            collaboratorRepository.save(new Collaborator("atcc","atcc@email.com",passwordEncoder.encode("atcc123"),   Set.of(Role.ATCC)));
            collaboratorRepository.save(new Collaborator("backoffice","backoffice@email.com",passwordEncoder.encode("backoffice123"),   Set.of(Role.BACKOFFICE)));
            collaboratorRepository.save(new Collaborator("technician","technician@email.com",passwordEncoder.encode("technician123"),   Set.of(Role.MAINTENANCE_TECHNICIAN)));
            collaboratorRepository.save(new Collaborator("supervisor","supervisor@email.com",passwordEncoder.encode("supervisor123"),   Set.of(Role.MAINTENANCE_SUPERVISOR)));
        }

        if(aircraftModelRepository.count() == 0) {
            aircraftModelRepository.save(new AircraftModel("Boeing 737-800", "Boeing",
                    new AircraftSpecs(26020.0,5436.0,842.0),
                        List.of("https://example.com/images/b737-front.jpg",
                                "https://example.com/images/b737-side.jpg")));
            aircraftModelRepository.save(new AircraftModel("Airbus A320neo","Airbus",
                    new AircraftSpecs(24210.0, 6300.0, 828.0),
                        List.of("https://example.com/images/a320neo-1.jpg",
                            "https://example.com/images/a320neo-2.jpg")));
            aircraftModelRepository.save(new AircraftModel("Boeing 787-9 Dreamliner","Boeing",
                    new AircraftSpecs(126000.0,14140.0,913.0),
                        List.of("https://example.com/images/b787-1.jpg",
                            "https://example.com/images/b787-2.jpg")));
            aircraftModelRepository.save(new AircraftModel("Embraer E195-E2", "Embraer",
                    new AircraftSpecs(13200.0, 4815.0, 870.0),
                        List.of("https://example.com/images/e195e2.jpg")));
        }

        if(airportRepository.count()==0){
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
                                    "Cargo Area",
                                    "Restaurants",
                                    "WiFi"
                            ),
                            35
                    ),
                    313.0,
                    List.of(
                            new Runway(
                                    null,
                                    "17/35",
                                    3480.0,
                                    "17/35",
                                    RunwayStatus.OPEN
                            )
                    ),
                    List.of(),
                    List.of(),
                    List.of(
                            "https://example.com/porto-airport.jpg"
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
                                    "Duty Free",
                                    "WiFi",
                                    "Medical Assistance"
                            ),
                            47
                    ),
                    0.0,
                    List.of(
                            new Runway(
                                    null,
                                    "03/21",
                                    3805.0,
                                    "03/21",
                                    RunwayStatus.OPEN
                            )
                    ),
                    List.of(
                            new Certification(
                                    "ICAO Safety Certification",
                                    "Safety",
                                    LocalDate.of(2028,1,1),
                                    LocalDate.of(2024,1,1)
                            )
                    ),
                    List.of(new Contact(ContactType.PHONE,"9999")),
                    List.of(
                            "https://example.com/lisbon-airport-1.jpg"
                    )
            ));
        }
    }
}
