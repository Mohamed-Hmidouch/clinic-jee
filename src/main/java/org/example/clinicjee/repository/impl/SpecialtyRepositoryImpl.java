package org.example.clinicjee.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Specialty;
import org.example.clinicjee.repository.SpecialtyRepository;

import java.util.Optional;

/**
 * Implémentation du repository Specialty
 */
public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    /**
     * Trouve une spécialité par son ID
     */
    @Override
    public Optional<Specialty> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Specialty specialty = em.find(Specialty.class, id);
            return Optional.ofNullable(specialty);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
}
