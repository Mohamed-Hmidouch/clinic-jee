package org.example.clinicjee.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * MedicalNote: id, contenu, validée, crééeLe
 * Relations: 
 *  - MedicalNote (*) → (1) Doctor (FK doctor_id)
 *  - MedicalNote (1) → (1) Appointment (FK appointment_id)
 */
@Entity
@Table(name = "medical_notes")
public class MedicalNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    private Boolean validee;
    private LocalDateTime creeeLe;
    
    // Relation: MedicalNote (1) → (1) Appointment
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;
    
    // Relation: MedicalNote (*) → (1) Doctor (FK doctor_id)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    public MedicalNote() {
    }

    public MedicalNote(Long id, String contenu, Boolean validee, LocalDateTime creeeLe,  Appointment appointment) {
        this.id = id;
        this.contenu = contenu;
        this.validee = validee;
        this.creeeLe = creeeLe;
        this.appointment = appointment;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Boolean getValidee() {
        return validee;
    }

    public void setValidee(Boolean validee) {
        this.validee = validee;
    }

    public LocalDateTime getCreeeLe() {
        return creeeLe;
    }

    public void setCreeeLe(LocalDateTime creeeLe) {
        this.creeeLe = creeeLe;
    }
    
    // Getters et Setters pour la relation doctor
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Appointment getAppointment() { return appointment; }

    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
}
