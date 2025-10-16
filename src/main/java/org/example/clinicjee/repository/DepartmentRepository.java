package org.example.clinicjee.repository;

import jakarta.persistence.EntityManager;
import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Department;

import java.util.List;
import java.util.Optional;

public class DepartmentRepository {

    public Optional<Department> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Department department = em.find(Department.class, id);
            return Optional.ofNullable(department);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d ORDER BY d.nom", Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
