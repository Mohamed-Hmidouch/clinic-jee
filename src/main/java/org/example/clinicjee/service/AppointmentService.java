package org.example.clinicjee.service;

import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.domain.enums.TypeRendezVous;
import org.example.clinicjee.repository.AppointmentRepository;
import org.example.clinicjee.repository.AvailabilityRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des rendez-vous
 */
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;

    public AppointmentService() {
        this.appointmentRepository = new AppointmentRepository();
        this.availabilityRepository = new AvailabilityRepository();
    }

    // TODO: Ajouter les méthodes selon les besoins
}