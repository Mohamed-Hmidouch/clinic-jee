

package org.example.clinicjee.repository.interfaces;

import org.example.clinicjee.domain.Availability;
import org.example.clinicjee.domain.enums.JourSemaine;
import java.util.Optional;

public interface AvailabilityRepositoryInterface {
    Optional<Availability> findByDoctorIdAndJour(Long doctorId, JourSemaine jour);
    Availability save(Availability availability);
    Availability update(Availability availability);
}
