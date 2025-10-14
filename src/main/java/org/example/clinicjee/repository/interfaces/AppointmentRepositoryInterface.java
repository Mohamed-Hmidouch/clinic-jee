package org.example.clinicjee.repository.interfaces;

import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.domain.enums.TypeRendezVous;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface pour la gestion des rendez-vous (Appointments)
 * Respecte the Single Responsibility Principle
 */
public interface AppointmentRepositoryInterface {

    // ========== CORE CRUD OPERATIONS ==========
    
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    Appointment update(Appointment appointment);
    void delete(Long id);
    List<Appointment> findAll(int page, int size);

    // ========== RECHERCHE PAR PATIENT ==========
    
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByPatientIdAndStatut(Long patientId, StatutRendezVous statut);
    List<Appointment> findUpcomingByPatientId(Long patientId);

    // ========== RECHERCHE PAR DOCTEUR ==========
    
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findByDoctorIdAndStatut(Long doctorId, StatutRendezVous statut);
    List<Appointment> findUpcomingByDoctorId(Long doctorId);

    // ========== RECHERCHE PAR DATE ==========
    
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // ========== RECHERCHE PAR STATUT ET TYPE ==========
    
    List<Appointment> findByStatut(StatutRendezVous statut);
    List<Appointment> findByType(TypeRendezVous type);

    // ========== VÉRIFICATION DE DISPONIBILITÉ ==========
    
    boolean isTimeSlotAvailable(Long doctorId, LocalDate date, LocalTime heure, Integer duree);
    boolean existsByDoctorIdAndDateAndHeure(Long doctorId, LocalDate date, LocalTime heure);
    int countByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findConflicts(Long doctorId, LocalDate date, LocalTime heure, Integer duree);

    // ========== STATISTIQUES ==========
    
    long count();
    long countByDoctorId(Long doctorId);
    long countByPatientId(Long patientId);
    long countByStatut(StatutRendezVous statut);
}
