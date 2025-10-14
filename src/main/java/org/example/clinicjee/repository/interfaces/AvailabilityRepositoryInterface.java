package org.example.clinicjee.repository.interfaces;

import org.example.clinicjee.domain.Availability;
import org.example.clinicjee.domain.enums.JourSemaine;
import java.util.Optional;

public interface AvailabilityRepositoryInterface {
    
    /**
     * ÉTAPE 1 : Récupérer l'Availability du docteur pour un jour donné
     * Utilisée pour: Obtenir les horaires de travail (heureDebut, heureFin, pauseDebut, pauseFin)
     */
    Optional<Availability> findByDoctorIdAndJour(Long doctorId, JourSemaine jour);
}
