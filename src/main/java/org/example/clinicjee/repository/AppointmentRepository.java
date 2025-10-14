package org.example.clinicjee.repository;

import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.domain.enums.TypeRendezVous;
import org.example.clinicjee.repository.interfaces.AppointmentRepositoryInterface;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository implements AppointmentRepositoryInterface {
    @Override public Appointment save(Appointment appointment) { return null; }
    @Override public Optional<Appointment> findById(Long id) { return Optional.empty(); }
    @Override public Appointment update(Appointment appointment) { return null; }
    @Override public void delete(Long id) { }
    @Override public List<Appointment> findAll(int page, int size) { return null; }
    @Override public List<Appointment> findByPatientId(Long patientId) { return null; }
    @Override public List<Appointment> findByPatientIdAndStatut(Long patientId, StatutRendezVous statut) { return null; }
    @Override public List<Appointment> findUpcomingByPatientId(Long patientId) { return null; }
    @Override public List<Appointment> findByDoctorId(Long doctorId) { return null; }
    @Override public List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date) { return null; }
    @Override public List<Appointment> findByDoctorIdAndStatut(Long doctorId, StatutRendezVous statut) { return null; }
    @Override public List<Appointment> findUpcomingByDoctorId(Long doctorId) { return null; }
    @Override public List<Appointment> findByDate(LocalDate date) { return null; }
    @Override public List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate) { return null; }
    @Override public List<Appointment> findByStatut(StatutRendezVous statut) { return null; }
    @Override public List<Appointment> findByType(TypeRendezVous type) { return null; }
    @Override public boolean isTimeSlotAvailable(Long doctorId, LocalDate date, LocalTime heure, Integer duree) { return false; }
    @Override public boolean existsByDoctorIdAndDateAndHeure(Long doctorId, LocalDate date, LocalTime heure) { return false; }
    @Override public int countByDoctorIdAndDate(Long doctorId, LocalDate date) { return 0; }
    @Override public List<Appointment> findConflicts(Long doctorId, LocalDate date, LocalTime heure, Integer duree) { return null; }
    @Override public long count() { return 0; }
    @Override public long countByDoctorId(Long doctorId) { return 0; }
    @Override public long countByPatientId(Long patientId) { return 0; }
    @Override public long countByStatut(StatutRendezVous statut) { return 0; }
}
