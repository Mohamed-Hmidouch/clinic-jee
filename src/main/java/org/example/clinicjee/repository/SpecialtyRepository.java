package org.example.clinicjee.repository;

import jakarta.persistence.EntityManager;
import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Specialty;
import org.example.clinicjee.repository.interfaces.SpecialtyRepositoryInterface;
import java.util.List;
import java.util.Optional;

public class SpecialtyRepository implements SpecialtyRepositoryInterface {

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
    
    public List<Specialty> findByDepartmentId(Long departmentId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT s FROM Specialty s WHERE s.department.id = :departmentId ORDER BY s.nom", 
                Specialty.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
        } finally {
            em.close();
        }
    }
}
