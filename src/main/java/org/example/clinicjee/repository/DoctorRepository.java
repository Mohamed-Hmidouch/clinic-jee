package org.example.clinicjee.repository;

import jakarta.persistence.EntityManager;
import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Doctor;

import java.util.List;
import java.util.Optional;

public class DoctorRepository {

    public Optional<Doctor> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Doctor doctor = em.find(Doctor.class, id);
            return Optional.ofNullable(doctor);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public List<Doctor> findBySpecialtyId(Long specialtyId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT d FROM Doctor d WHERE d.specialite.id = :specialtyId ORDER BY d.fullName", 
                Doctor.class)
                .setParameter("specialtyId", specialtyId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Doctor> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Doctor d ORDER BY d.fullName", Doctor.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
