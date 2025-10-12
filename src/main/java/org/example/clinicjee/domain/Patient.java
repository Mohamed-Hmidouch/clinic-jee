package org.example.clinicjee.domain;

import jakarta.persistence.*;
import org.example.clinicjee.domain.enums.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient: id, cin, naissance
 * Relation: Patient (1) → (*) Appointment
 */
@Entity
@Table(name = "patients")
public class Patient extends User{
    @Column(nullable = false, unique = true)
    private String cin;
    private LocalDate naissance;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public Patient() {
        super(); // Appel explicite du constructeur par défaut de User
    }

    public Patient(String fullName, String email, String passwordHash, 
                   String cin, LocalDate naissance) {
        super(null, fullName, email, passwordHash, Role.PATIENT); // Appel du constructeur de User
        this.cin = cin;
        this.naissance = naissance;
    }

    // Getters et Setters
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    // Getters et Setters pour la relation appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
