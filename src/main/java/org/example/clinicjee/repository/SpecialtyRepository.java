package org.example.clinicjee.repository;

import org.example.clinicjee.domain.Specialty;

import java.util.Optional;

public interface SpecialtyRepository {
    Optional<Specialty> findById(Long id);
}
