package org.example.clinicjee.repository;

import org.example.clinicjee.domain.Availability;
import org.example.clinicjee.domain.enums.JourSemaine;
import org.example.clinicjee.repository.interfaces.AvailabilityRepositoryInterface;
import java.util.Optional;

public class AvailabilityRepository implements AvailabilityRepositoryInterface {

    @Override
    public Optional<Availability> findByDoctorIdAndJour(Long doctorId, JourSemaine jour) {
        // TODO: Implémenter la méthode
        return Optional.empty();
    }
}
