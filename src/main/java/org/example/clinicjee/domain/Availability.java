package org.example.clinicjee.domain;

import jakarta.persistence.*;
import org.example.clinicjee.domain.enums.JourSemaine;
import org.example.clinicjee.domain.enums.StatutDisponibilite;
import java.time.LocalTime;

/**
 * Availability: id, jour(enum), heureDébut, heureFin, statut(enum), validité
 */
@Entity
@Table(name = "availabilities")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private JourSemaine jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    
    @Column(name = "pause_debut")
    private LocalTime pauseDebut;
    
    @Column(name = "pause_fin")
    private LocalTime pauseFin;
    
    @Enumerated(EnumType.STRING)
    private StatutDisponibilite statut;
    private Boolean validite;
    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    public Availability() {
    }

    public Availability(Long id, JourSemaine jour, LocalTime heureDebut, LocalTime heureFin, LocalTime pauseDebut, LocalTime pauseFin, StatutDisponibilite statut, Boolean validite) {
        this.id = id;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.pauseDebut = pauseDebut;
        this.pauseFin = pauseFin;
        this.statut = statut;
        this.validite = validite;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JourSemaine getJour() {
        return jour;
    }

    public void setJour(JourSemaine jour) {
        this.jour = jour;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public LocalTime getPauseDebut() {
        return pauseDebut;
    }

    public void setPauseDebut(LocalTime pauseDebut) {
        this.pauseDebut = pauseDebut;
    }

    public LocalTime getPauseFin() {
        return pauseFin;
    }

    public void setPauseFin(LocalTime pauseFin) {
        this.pauseFin = pauseFin;
    }

    public StatutDisponibilite getStatut() {
        return statut;
    }

    public void setStatut(StatutDisponibilite statut) {
        this.statut = statut;
    }

    public Boolean getValidite() {
        return validite;
    }

    public void setValidite(Boolean validite) {
        this.validite = validite;
    }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}
