package com.example.AISafePSOFT_26.Aircraft.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="company_aircrafts")
public class Aircraft {
    @Id
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    private AircraftAvailability status;

    @Column(nullable = false)
    private LocalDate manufacturingDate;

    private Double totalOperationalHours;
    private Double totalFlightHours;
    private Double meanRange;

    @ElementCollection
    private List<String> features = new ArrayList<>();

    @Embedded
    private SeatingPack seatingPack;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstalledComponent> components = new ArrayList<>();

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AircraftCertification> certifications = new ArrayList<>();

    protected Aircraft() {}

    public Aircraft(String registrationNumber, LocalDate manufacturingDate, Double totalOperationalHours, Double totalFlightHours) {
        this.registrationNumber = registrationNumber;
        this.manufacturingDate = manufacturingDate;
        this.totalOperationalHours = totalOperationalHours;
        this.totalFlightHours = totalFlightHours;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public AircraftAvailability getStatus() {
        return status;
    }

    public void setStatus(AircraftAvailability status) {
        this.status = status;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Double getTotalOperationalHours() {
        return totalOperationalHours;
    }

    public void setTotalOperationalHours(Double totalOperationalHours) {
        this.totalOperationalHours = totalOperationalHours;
    }

    public Double getMeanRange() {
        return meanRange;
    }

    public void setMeanRange(Double meanRange) {
        this.meanRange = meanRange;
    }

    public Double getTotalFlightHours() {
        return totalFlightHours;
    }

    public void setTotalFlightHours(Double totalFlightHours) {
        this.totalFlightHours = totalFlightHours;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<InstalledComponent> getComponents() {
        return components;
    }

    public void setComponents(List<InstalledComponent> components) {
        this.components = components;
    }

    public SeatingPack getSeatingPack() {
        return seatingPack;
    }

    public void setSeatingPack(SeatingPack seatingPack) {
        this.seatingPack = seatingPack;
    }

    public List<AircraftCertification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<AircraftCertification> certifications) {
        this.certifications = certifications;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void recordFlight(Double distance, Double hours) {
        this.totalFlightHours += hours;
        this.totalOperationalHours += hours;
        this.meanRange = distance / hours;
    }

    public void updateStatus(AircraftAvailability status) {
        this.status = status;
    }

    public void updateSeatingPack(SeatingPack seatingPack) {
        this.seatingPack = seatingPack;
    }

}