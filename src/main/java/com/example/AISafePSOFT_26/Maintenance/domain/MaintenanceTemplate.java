package com.example.AISafePSOFT_26.Maintenance.domain;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class MaintenanceTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    private String name;

    @Column(nullable = false)
    private Double expectedDuration;

    @ElementCollection
    @CollectionTable(name = "maintenance_template_checklist")
    @MapKeyColumn(name = "check_item")
    @Column(name = "is_required")
    private Map<String, Boolean> templateChecklist;

    @Enumerated(EnumType.STRING)
    private MaintenanceType operation;

    @Enumerated(EnumType.STRING)
    private MaintenanceAttribute attribute;
}
