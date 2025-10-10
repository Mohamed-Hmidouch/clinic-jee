package org.example.clinicjee.domain;

import jakarta.persistence.*;
import org.example.clinicjee.domain.enums.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Doctor: id, matricule, titre, spécialité
 */
@Entity
@Table(name = "doctors")
public class Doctor extends User{
    private String matricule;
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialite;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
    
    // Relation: Doctor (1) → (*) MedicalNote
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalNote> medicalNotes = new ArrayList<>();

    public Doctor() {
        super(); // Appel explicite du constructeur par défaut de User
    }

    public Doctor(String fullName, String email, String passwordHash,
                  String matricule, String titre, Specialty specialite) {
        super(null, fullName, email, passwordHash, Role.DOCTEUR); // Appel du constructeur de User
        this.matricule = matricule;
        this.titre = titre;
        this.specialite = specialite;
    }

    // Getters et Setters

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Specialty getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialty specialite) {
        this.specialite = specialite;
    }
    
    // Getters et Setters pour la relation medicalNotes
    public List<MedicalNote> getMedicalNotes() {
        return medicalNotes;
    }
    
    public void setMedicalNotes(List<MedicalNote> medicalNotes) {
        this.medicalNotes = medicalNotes;
    }
}
