package org.example.clinicjee.repository;

import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.repository.interfaces.AppointmentRepositoryInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les rendez-vous (Appointments)
 */
public class AppointmentRepository implements AppointmentRepositoryInterface {

    /**
     * Récupère TOUS les rendez-vous d'un docteur pour une date précise
     * IMPORTANT: Retourne une LISTE car un docteur peut avoir plusieurs RDV par jour
     * 
     * @param doctorId L'ID du docteur
     * @param date La date du rendez-vous (ex: 2025-10-17)
     * @return Liste des rendez-vous pour ce docteur à cette date
     */
    public List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL corrigé: a.doctor.id au lieu de a.doctor_id
            return em.createQuery(
                "SELECT a FROM Appointment a " +
                "WHERE a.doctor.id = :doctorId " +
                "AND a.date = :date " +
                "AND a.statut != :statutAnnule " +
                "ORDER BY a.heure ASC", 
                Appointment.class)
                .setParameter("doctorId", doctorId)
                .setParameter("date", date)
                .setParameter("statutAnnule", StatutRendezVous.ANNULE)
                .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupère tous les rendez-vous d'un docteur (toutes dates confondues)
     * 
     * @param doctorId L'ID du docteur
     * @return Liste de tous les rendez-vous du docteur
     */
    public List<Appointment> findAllByDoctorId(Long doctorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT a FROM Appointment a " +
                "WHERE a.doctor.id = :doctorId " +
                "ORDER BY a.date DESC, a.heure DESC", 
                Appointment.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Vérifie si un créneau est déjà occupé
     * Utilisé avant de créer un nouveau rendez-vous
     * 
     * @param doctorId L'ID du docteur
     * @param date La date du rendez-vous
     * @param heure L'heure de début du rendez-vous
     * @return true si le créneau est libre, false sinon
     */
    public boolean isSlotAvailable(Long doctorId, LocalDate date, LocalTime heure) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(a) FROM Appointment a " +
                "WHERE a.doctor.id = :doctorId " +
                "AND a.date = :date " +
                "AND a.heure = :heure " +
                "AND a.statut != :statutAnnule", 
                Long.class)
                .setParameter("doctorId", doctorId)
                .setParameter("date", date)
                .setParameter("heure", heure)
                .setParameter("statutAnnule", StatutRendezVous.ANNULE)
                .getSingleResult();
            
            return count == 0;
        } finally {
            em.close();
        }
    }

    /**
     * Sauvegarde un nouveau rendez-vous
     * 
     * @param appointment Le rendez-vous à sauvegarder
     * @return Le rendez-vous sauvegardé avec son ID généré
     */

    public Appointment save(Appointment appointment) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            // Définir les timestamps
            if (appointment.getCreatedAt() == null) {
                appointment.setCreatedAt(LocalDateTime.now());
            }
            appointment.setUpdatedAt(LocalDateTime.now());
            
            em.persist(appointment);
            transaction.commit();
            
            return appointment;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du rendez-vous", e);
        } finally {
            em.close();
        }
    }

    /**
     * Trouve un rendez-vous par son ID
     * 
     * @param id L'ID du rendez-vous
     * @return Optional contenant le rendez-vous s'il existe
     */
    public Optional<Appointment> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Appointment appointment = em.find(Appointment.class, id);
            return Optional.ofNullable(appointment);
        } finally {
            em.close();
        }
    }

    /**
     * Met à jour un rendez-vous existant
     * 
     * @param appointment Le rendez-vous à mettre à jour
     * @return Le rendez-vous mis à jour
     */
    public Appointment update(Appointment appointment) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            
            appointment.setUpdatedAt(LocalDateTime.now());
            Appointment updated = em.merge(appointment);
            
            transaction.commit();
            return updated;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du rendez-vous", e);
        } finally {
            em.close();
        }
    }

    public List<Appointment> findByPatientId(Long id){
        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Appointment> appointments = em.createQuery(
            "SELECT a FROM Appointment a WHERE a.patient.id = :patientId ORDER BY a.date DESC, a.heure DESC",
            Appointment.class)
            .setParameter("patientId", id)
            .getResultList();
            return appointments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des rendez-vous pour le patient avec l'id: " + id, e);
        }finally{
            em.close();
        }
    }
    
    /**
     * Récupère les rendez-vous d'un patient filtrés par statut
     * Charge complètement doctor et specialty pour éviter lazy loading
     */
    public List<Appointment> findByPatientIdAndStatus(Long patientId, StatutRendezVous status){
        EntityManager em = JPAUtil.getEntityManager();

        try {
            // Charger toutes les relations nécessaires avec JOIN FETCH
            List<Appointment> appointments = em.createQuery(
            "SELECT DISTINCT a FROM Appointment a " +
            "JOIN FETCH a.doctor d " +
            "LEFT JOIN FETCH d.specialite " +
            "WHERE a.patient.id = :patientId AND a.statut = :status " +
            "ORDER BY a.date ASC, a.heure ASC",
            Appointment.class)
            .setParameter("patientId", patientId)
            .setParameter("status", status)
            .getResultList();
            
            // Forcer l'initialisation des propriétés lazy de User (parent de Doctor)
            for (Appointment apt : appointments) {
                if (apt.getDoctor() != null) {
                    // Accès aux propriétés pour forcer le chargement
                    apt.getDoctor().getFullName();
                    apt.getDoctor().getTitre();
                    if (apt.getDoctor().getSpecialite() != null) {
                        apt.getDoctor().getSpecialite().getNom();
                    }
                }
            }
            
            return appointments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des rendez-vous planifiés pour le patient avec l'id: " + patientId, e);
        }finally{
            em.close();
        }
    }
    
    /**
     * Récupère les rendez-vous d'un patient avec pagination
     * @param patientId ID du patient
     * @param status Statut des rendez-vous (peut être null pour tous)
     * @param page Numéro de page (commence à 0)
     * @param pageSize Nombre d'éléments par page
     * @return Liste paginée des rendez-vous
     */
    public List<Appointment> findByPatientIdAndStatusWithPagination(
            Long patientId, StatutRendezVous status, int page, int pageSize) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT DISTINCT a FROM Appointment a " +
                         "JOIN FETCH a.doctor d " +
                         "LEFT JOIN FETCH d.specialite " +
                         "WHERE a.patient.id = :patientId ";
            
            if (status != null) {
                jpql += "AND a.statut = :status ";
            }
            
            jpql += "ORDER BY a.date DESC, a.heure DESC";
            
            var query = em.createQuery(jpql, Appointment.class)
                         .setParameter("patientId", patientId)
                         .setFirstResult(page * pageSize)  // Offset
                         .setMaxResults(pageSize);         // Limit
            
            if (status != null) {
                query.setParameter("status", status);
            }
            
            List<Appointment> appointments = query.getResultList();
            
            // Forcer l'initialisation
            for (Appointment apt : appointments) {
                if (apt.getDoctor() != null) {
                    apt.getDoctor().getFullName();
                    apt.getDoctor().getTitre();
                    if (apt.getDoctor().getSpecialite() != null) {
                        apt.getDoctor().getSpecialite().getNom();
                    }
                }
            }
            
            return appointments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération paginée des rendez-vous", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Compte le nombre total de rendez-vous d'un patient
     * @param patientId ID du patient
     * @param status Statut des rendez-vous (peut être null pour tous)
     * @return Nombre total de rendez-vous
     */
    public long countByPatientIdAndStatus(Long patientId, StatutRendezVous status) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT COUNT(a) FROM Appointment a WHERE a.patient.id = :patientId";
            
            if (status != null) {
                jpql += " AND a.statut = :status";
            }
            
            var query = em.createQuery(jpql, Long.class)
                         .setParameter("patientId", patientId);
            
            if (status != null) {
                query.setParameter("status", status);
            }
            
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du comptage des rendez-vous", e);
        } finally {
            em.close();
        }
    }
}