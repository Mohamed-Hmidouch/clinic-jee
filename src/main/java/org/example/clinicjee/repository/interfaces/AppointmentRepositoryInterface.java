package org.example.clinicjee.repository.interfaces;

import org.example.clinicjee.domain.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryInterface {
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findAllByDoctorId(Long doctorId);
    boolean isSlotAvailable(Long doctorId, LocalDate date, LocalTime heure);
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    Appointment update(Appointment appointment);
}