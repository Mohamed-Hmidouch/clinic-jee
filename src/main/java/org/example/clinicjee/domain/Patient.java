package org.example.clinicjee.domain;

import jakarta.persistence.*;
import org.example.clinicjee.domain.enums.Role;
import org.example.clinicjee.domain.enums.Sexe;
import org.example.clinicjee.domain.enums.GroupeSanguin;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient: id, cin, naissance, sexe(enum), adresse, téléphone, sang(enum)
 * Relation: Patient (1) → (*) Appointment
 */
@Entity
@Table(name = "patients")
public class Patient extends User{
    @Column(nullable = false, unique = true)
    private String cin;
    private LocalDate naissance;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    private String adresse;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private GroupeSanguin sang;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public Patient() {
        super(); // Appel explicite du constructeur par défaut de User
    }

    public Patient(String fullName, String email, String passwordHash, 
                   String cin, LocalDate naissance, Sexe sexe, String adresse, String telephone, GroupeSanguin sang) {
        super(null, fullName, email, passwordHash, Role.PATIENT); // Appel du constructeur de User
        this.cin = cin;
        this.naissance = naissance;
        this.sexe = sexe;
        this.adresse = adresse;
        this.telephone = telephone;
        this.sang = sang;
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

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public GroupeSanguin getSang() {
        return sang;
    }

    public void setSang(GroupeSanguin sang) {
        this.sang = sang;
    }
    
    // Getters et Setters pour la relation appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
