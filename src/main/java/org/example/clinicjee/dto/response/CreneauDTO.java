package org.example.clinicjee.dto.response;

import java.time.LocalTime;

public class CreneauDTO {
    private LocalTime heure;           // Ex: 09:00
    private boolean disponible;        // true = vert, false = gris
    
    // Constructeurs
    public CreneauDTO() {}
    
    public CreneauDTO(LocalTime heure, boolean disponible) {
        this.heure = heure;
        this.disponible = disponible;
    }
    
    // Getters et Setters
    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}