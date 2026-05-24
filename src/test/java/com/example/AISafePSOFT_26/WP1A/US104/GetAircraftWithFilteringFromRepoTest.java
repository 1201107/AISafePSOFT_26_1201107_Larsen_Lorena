package com.example.AISafePSOFT_26.WP1A.US104;

import com.example.AISafePSOFT_26.Aircraft.HangarController;
import com.example.AISafePSOFT_26.Aircraft.application.AddAircraftUseCase;
import com.example.AISafePSOFT_26.Aircraft.application.AircraftLifeCycleUpdaterService;
import com.example.AISafePSOFT_26.Aircraft.application.AircraftSearchService;
import com.example.AISafePSOFT_26.Aircraft.domain.Aircraft;
import com.example.AISafePSOFT_26.Aircraft.domain.AircraftAvailability;
import com.example.AISafePSOFT_26.AircraftCatalog.application.AircraftModelSearchService;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAircraftWithFilteringFromRepoTest {

    private AircraftSearchService aircraftSearchService;
    private AircraftLifeCycleUpdaterService aircraftLifeCycleUpdaterService;
    private HangarController hangarController;

    @BeforeEach
    void setUp() {

        aircraftSearchService = mock(AircraftSearchService.class);

        aircraftLifeCycleUpdaterService =
                new AircraftLifeCycleUpdaterService();

        hangarController = new HangarController(
                mock(AddAircraftUseCase.class),
                mock(AircraftModelSearchService.class),
                aircraftLifeCycleUpdaterService,
                aircraftSearchService
        );
    }

    @Test
    void shouldReturnFilteredAircrafts() {

        AircraftModel model = mock(AircraftModel.class);

        when(model.getModelName()).thenReturn("Airbus A320");

        Aircraft aircraft = new Aircraft(
                "CS-TST",
                model,
                LocalDate.of(2020, 1, 1),
                1000.0,
                800.0
        );

        aircraftLifeCycleUpdaterService.changeAvailability(
                aircraft,
                AircraftAvailability.AVAILABLE
        );

        when(aircraftSearchService.findAircraftsMatchingFilter(
                "Airbus A320",
                "AVAILABLE",
                LocalDate.of(2020, 1, 1)
        )).thenReturn(List.of(aircraft));

        List<HangarController.AircraftResponse> response =
                hangarController.getAircraftsWithFiltering(
                        "Airbus A320",
                        "AVAILABLE",
                        LocalDate.of(2020, 1, 1)
                );

        assertNotNull(response);
        assertEquals(1, response.size());

        HangarController.AircraftResponse aircraftResponse =
                response.get(0);

        assertEquals("CS-TST",
                aircraftResponse.registrationNumber());

        assertEquals("Airbus A320",
                aircraftResponse.modelName());

        assertEquals(AircraftAvailability.AVAILABLE,
                aircraftResponse.status());

        assertEquals(LocalDate.of(2020, 1, 1),
                aircraftResponse.manufacturingDate());

        assertEquals(1000.0,
                aircraftResponse.totalOperationalHours());

        assertEquals(800.0,
                aircraftResponse.totalFlightHours());

        verify(aircraftSearchService, times(1))
                .findAircraftsMatchingFilter(
                        "Airbus A320",
                        "AVAILABLE",
                        LocalDate.of(2020, 1, 1)
                );
    }
}