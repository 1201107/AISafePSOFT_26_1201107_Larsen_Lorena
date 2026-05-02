package com.example.AISafePSOFT_26.Users.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "technicians")
public class Technician extends Collaborator {

    public Technician() {}

    public Technician(String username, String email, String password, Set<Role> roles) {
        super(username, email, password, roles);
    }
}