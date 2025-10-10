package org.example.clinicjee.domain;

import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Settings: duréeCréneau, buffer, pauseDébut/Fin, leadTime, annulationLimite, joursNonTravaillés
 */

@Entity
@Table(name = "settings")
public class Settings implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dureeCreneau;           // en minutes
    private Integer buffer;                 // temps entre rendez-vous (minutes)

    private LocalTime pauseDebut;
    private LocalTime pauseFin;

    private Integer leadTime;               // délai minimum pour prendre RDV (heures)
    private Integer annulationLimite;       // délai pour annuler (heures)

    @ElementCollection
    @CollectionTable(name = "settings_jours_non_travailles", joinColumns = @JoinColumn(name = "settings_id"))
    @Column(name = "jour_non_travaille")
    private List<String> joursNonTravailles; // ex: ["SAMEDI", "DIMANCHE"]

    public Settings() {
    }

    public Settings(Long id, Integer dureeCreneau, Integer buffer, LocalTime pauseDebut, LocalTime pauseFin, Integer leadTime, Integer annulationLimite, List<String> joursNonTravailles) {
        this.id = id;
        this.dureeCreneau = dureeCreneau;
        this.buffer = buffer;
        this.pauseDebut = pauseDebut;
        this.pauseFin = pauseFin;
        this.leadTime = leadTime;
        this.annulationLimite = annulationLimite;
        this.joursNonTravailles = joursNonTravailles;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDureeCreneau() {
        return dureeCreneau;
    }

    public void setDureeCreneau(Integer dureeCreneau) {
        this.dureeCreneau = dureeCreneau;
    }

    public Integer getBuffer() {
        return buffer;
    }

    public void setBuffer(Integer buffer) {
        this.buffer = buffer;
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

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Integer getAnnulationLimite() {
        return annulationLimite;
    }

    public void setAnnulationLimite(Integer annulationLimite) {
        this.annulationLimite = annulationLimite;
    }

    public List<String> getJoursNonTravailles() {
        return joursNonTravailles;
    }

    public void setJoursNonTravailles(List<String> joursNonTravailles) {
        this.joursNonTravailles = joursNonTravailles;
    }
}
