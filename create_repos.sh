#!/bin/bash

# Création de AppointmentRepositoryInterface
cat > src/main/java/org/example/clinicjee/repository/interfaces/AppointmentRepositoryInterface.java << 'EOF'
package org.example.clinicjee.repository.interfaces;

import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.domain.enums.TypeRendezVous;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryInterface {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    Appointment update(Appointment appointment);
    void delete(Long id);
    List<Appointment> findAll(int page, int size);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByPatientIdAndStatut(Long patientId, StatutRendezVous statut);
    List<Appointment> findUpcomingByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findByDoctorIdAndStatut(Long doctorId, StatutRendezVous statut);
    List<Appointment> findUpcomingByDoctorId(Long doctorId);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Appointment> findByStatut(StatutRendezVous statut);
    List<Appointment> findByType(TypeRendezVous type);
    boolean isTimeSlotAvailable(Long doctorId, LocalDate date, LocalTime heure, Integer duree);
    boolean existsByDoctorIdAndDateAndHeure(Long doctorId, LocalDate date, LocalTime heure);
    int countByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findConflicts(Long doctorId, LocalDate date, LocalTime heure, Integer duree);
    long count();
    long countByDoctorId(Long doctorId);
    long countByPatientId(Long patientId);
    long countByStatut(StatutRendezVous statut);
}
EOF

echo "✅ AppointmentRepositoryInterface créé"

