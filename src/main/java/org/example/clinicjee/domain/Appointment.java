package org.example.clinicjee.domain;

import jakarta.persistence.*;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.domain.enums.TypeRendezVous;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Appointment: id, date, heure, statut(enum), type(enum), createdAt, updatedAt
 * Relation: Appointment (*) → (1) Patient (FK patient_id)
 */
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime heure;
    
    @Column(nullable = false)
    private Integer duree; // Durée en minutes
    
    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut;
    @Enumerated(EnumType.STRING)
    private TypeRendezVous type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Relation: Appointment (*) → (1) Patient (FK patient_id)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    // Relation: Appointment (*) → (1) Doctor (FK doctor_id)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    // Relation: Appointment (1) ↔ (0..1) MedicalNote
    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY, optional = true)
    private MedicalNote medicalNote;

    public Appointment() {
    }

    public Appointment(Long id, LocalDate date, LocalTime heure, Integer duree, StatutRendezVous statut, TypeRendezVous type, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.statut = statut;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(StatutRendezVous statut) {
        this.statut = statut;
    }

    public TypeRendezVous getType() {
        return type;
    }

    public void setType(TypeRendezVous type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Getters et Setters pour la relation patient
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    // Getters et Setters pour la relation doctor
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    // Getters et Setters pour la relation medicalNote
    public MedicalNote getMedicalNote() {
        return medicalNote;
    }
    
    public void setMedicalNote(MedicalNote medicalNote) {
        this.medicalNote = medicalNote;
    }
}
