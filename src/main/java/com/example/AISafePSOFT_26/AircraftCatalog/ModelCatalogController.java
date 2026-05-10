package com.example.AISafePSOFT_26.AircraftCatalog;

import com.example.AISafePSOFT_26.AircraftCatalog.application.AddModelUseCase;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftModel;
import com.example.AISafePSOFT_26.AircraftCatalog.domain.AircraftSpecs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class ModelCatalogController {
    private final AddModelUseCase addModelUseCase;

    public ModelCatalogController(AddModelUseCase addModelUseCase) {
        this.addModelUseCase = addModelUseCase;
    }

    /**
     * Adds a new aircraft model to the catalog.
     */
    @PostMapping("/models")
    @ResponseStatus(HttpStatus.CREATED)
    public void addModel(@Valid @RequestBody AddModelRequest request) {
        AircraftSpecs specs = new AircraftSpecs(request.specs().fuelCapacityLiters(),request.specs().maximumRangeKm(),
                request.specs().cruisingSpeedKph(),request.specs().standardSeatingCapacity());

        AircraftModel model = new AircraftModel(request.modelName(), request.manufacturer(),
                specs, request.modelImage());
        addModelUseCase.execute(model);
    }

    /**
     * Request body for POST /catalog/models
     */
    record AddModelRequest(@NotBlank String modelName, List<String> modelImage,
            @NotBlank String manufacturer, @Valid ModelSpecsRequest specs) {}

    /**
     * Request fragment representing aircraft specs.
     */
    record ModelSpecsRequest(Double fuelCapacityLiters, Double maximumRangeKm,
            Double cruisingSpeedKph, Integer standardSeatingCapacity) {}

    /**
     * Response fragment representing aircraft specs.
     */
    record ModelSpecsResponse(Double fuelCapacityLiters, Double maximumRangeKm,
            Double cruisingSpeedKph, Integer standardSeatingCapacity) {
        static ModelSpecsResponse from(AircraftSpecs specs) {
            return new ModelSpecsResponse(specs.getFuelCapacity(), specs.getMaximumRange(),
                    specs.getCruisingSpeed(),specs.getStandardSeatingCapacity());
        }
    }

    /**
     * Response body representing an aircraft model.
     */
    record ModelResponse(String modelName, List<String> modelImage, String manufacturer, ModelSpecsResponse specs) {
        static ModelResponse from(AircraftModel model) {
            return new ModelResponse(model.getModelName(),model.getModelImage(),
                    model.getManufacturer(),ModelSpecsResponse.from(model.getAircraftModelSpecs()));
        }
    }
}