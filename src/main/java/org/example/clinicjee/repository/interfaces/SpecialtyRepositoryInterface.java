package org.example.clinicjee.repository.interfaces;

import org.example.clinicjee.domain.Specialty;

import java.util.Optional;

public interface SpecialtyRepositoryInterface {
    Optional<Specialty> findById(Long id);
}
