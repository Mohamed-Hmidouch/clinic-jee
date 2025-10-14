package org.example.clinicjee.repository;


import java.util.Optional;

import javax.swing.text.html.parser.Entity;
import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Patient;

import jakarta.persistence.EntityManager;

public class PatientRepository {

    public Optional<Patient> findById(Long patientId){
        EntityManager em = JPAUtil.getEntityManager();

        try {
            Patient patient = em.createQuery(
                "SELECT p FROM Patient p JOIN FETCH p.user u WHERE p.id = :patientId", Patient.class)
                .setParameter("patientId", patientId)
                .getSingleResult();
            return Optional.of(patient);
        } catch (Exception e) {
            throw new RuntimeException("Patient not found with id: " + patientId, e);
        } finally {
            em.close();
        }
    }
}
