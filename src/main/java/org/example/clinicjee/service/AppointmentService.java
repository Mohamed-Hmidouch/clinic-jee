package org.example.clinicjee.service;

import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.Availability;
import org.example.clinicjee.domain.Patient;
import org.example.clinicjee.domain.enums.JourSemaine;
import org.example.clinicjee.domain.enums.StatutRendezVous;
import org.example.clinicjee.domain.enums.TypeRendezVous;
import org.example.clinicjee.dto.response.CreneauDTO;
import org.example.clinicjee.repository.AppointmentRepository;
import org.example.clinicjee.repository.PatientRepository;
import org.example.clinicjee.repository.AvailabilityRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AvailabilityRepository availabilityRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository,
            AvailabilityRepository availabilityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.availabilityRepository = availabilityRepository;
    }

    // Convertir DayOfWeek (MONDAY, etc.) en JourSemaine (LUNDI, etc.)
    private JourSemaine convertDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return JourSemaine.LUNDI;
            case TUESDAY: return JourSemaine.MARDI;
            case WEDNESDAY: return JourSemaine.MERCREDI;
            case THURSDAY: return JourSemaine.JEUDI;
            case FRIDAY: return JourSemaine.VENDREDI;
            case SATURDAY: return JourSemaine.SAMEDI;
            case SUNDAY: return JourSemaine.DIMANCHE;
            default: throw new IllegalArgumentException("Jour invalide: " + dayOfWeek);
        }
    }

    // Récupérer les créneaux disponibles pour un docteur à une date donnée
    public List<CreneauDTO> getCreneauxDisponibles(Long doctorId, LocalDate date) {
        List<CreneauDTO> creneauxDisponibles = new ArrayList<>();
        JourSemaine jour = convertDayOfWeek(date.getDayOfWeek());
        Optional<Availability> optAvail = availabilityRepository.findByDoctorIdAndJour(doctorId, jour);

        if (optAvail.isEmpty()) {
            return creneauxDisponibles;
        }

        Availability avail = optAvail.get();
        LocalTime heureDebut = avail.getHeureDebut();
        LocalTime heureFin = avail.getHeureFin();
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);

        LocalTime current = heureDebut;
        while (current.isBefore(heureFin)) {
            final LocalTime slot = current;
            boolean isTaken = appointments.stream()
                .anyMatch(a -> a.getHeure().equals(slot));

            if (!isTaken) {
                creneauxDisponibles.add(new CreneauDTO(slot, true));
                current = current.plusMinutes(30);
            } else {
                // Sauter ce créneau + le suivant (60min)
                current = current.plusMinutes(60);
            }
        }

        return creneauxDisponibles;
        // exemple de return [
        // { heure: 09:00, disponible: true },
        // { heure: 09:30, disponible: true },
        // { heure: 10:00, disponible: true }
        // ]
    }

    // Créer un nouveau rendez-vous
    public Appointment createAppointment(Appointment appointment) {
        // 1️⃣ Vérifier que le patient existe
        if (appointment.getPatient() == null || appointment.getPatient().getId() == null) {
            throw new RuntimeException("Patient non fourni dans la requête");
        }
        Optional<Patient> optPatient = patientRepository.findById(appointment.getPatient().getId());
        if (optPatient.isEmpty()) {
            throw new RuntimeException("Patient non trouvé avec l'ID: " + appointment.getPatient().getId());
        }

        // 2️⃣ Vérifier si le créneau est disponible
        boolean isDisponible = isCreneauDisponible(
                appointment.getDoctor().getId(),
                appointment.getDate(),
                appointment.getHeure(),
                appointment.getType());

        if (!isDisponible) {
            throw new RuntimeException("Ce créneau n'est pas disponible");
        }

        // 3️⃣ Initialiser le statut à PLANIFIE
        appointment.setStatut(StatutRendezVous.PLANIFIE);

        // 4️⃣ Enregistrer le rendez-vous
        Appointment saved = appointmentRepository.save(appointment);

        return saved;
        // exemple de return {
        // id: 1,
        // doctorId: 5,
        // patientId: 12,
        // date: 2025-10-20,
        // heure: 09:00,
        // type: CONSULTATION,
        // statut: PLANIFIE
        // }
    }

    // Vérifier si un créneau est disponible
    public boolean isCreneauDisponible(Long doctorId, LocalDate date, LocalTime heure, TypeRendezVous type) {
        // 1️⃣ Vérifier que le docteur travaille ce jour
        JourSemaine jour = convertDayOfWeek(date.getDayOfWeek());
        Optional<Availability> optAvail = availabilityRepository.findByDoctorIdAndJour(doctorId, jour);

        if (optAvail.isEmpty()) {
            return false; // Le docteur ne travaille pas ce jour
        }

        // 2️⃣ Vérifier que l'heure est dans les horaires de travail
        Availability avail = optAvail.get();
        if (heure.isBefore(avail.getHeureDebut()) || heure.isAfter(avail.getHeureFin())) {
            return false; // Hors des heures de travail
        }

        // 3️⃣ Calculer la durée nécessaire selon le type
        int dureeMinutes = (type == TypeRendezVous.URGENCE) ? 60 : 30;
        LocalTime heureFin = heure.plusMinutes(dureeMinutes);

        // 4️⃣ Récupérer tous les rendez-vous du docteur pour cette date
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);

        // 5️⃣ Vérifier s'il y a un conflit avec un rendez-vous existant
        for (Appointment appt : appointments) {
            int dureeExistante = (appt.getType() == TypeRendezVous.URGENCE) ? 60 : 30;
            LocalTime apptFin = appt.getHeure().plusMinutes(dureeExistante);

            // Vérifier le chevauchement
            boolean hasConflict = heure.isBefore(apptFin) && heureFin.isAfter(appt.getHeure());
            if (hasConflict) {
                return false; // Il y a un conflit
            }
        }

        return true; // Le créneau est disponible
        // exemple de return true si disponible, false sinon
    }

    // Récupérer tous les rendez-vous d'un docteur pour une date
    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        // 1️⃣ Récupérer tous les rendez-vous du docteur pour la date
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);

        // 2️⃣ Trier par heure (du plus tôt au plus tard)
        appointments.sort((a1, a2) -> a1.getHeure().compareTo(a2.getHeure()));

        return appointments;
        // exemple de return [
        // { id: 1, patientId: 12, heure: 09:00, type: CONSULTATION, statut: PLANIFIE },
        // { id: 2, patientId: 15, heure: 10:30, type: URGENCE, statut: TERMINE },
        // { id: 3, patientId: 20, heure: 14:00, type: SUIVI, statut: PLANIFIE }
        // ]
    }

    // Récupérer tous les rendez-vous d'un patient
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        // 1️⃣ Vérifier que le patient existe
        Optional<Patient> optPatient = patientRepository.findById(patientId);
        if (optPatient.isEmpty()) {
            throw new RuntimeException("Patient non trouvé avec l'ID: " + patientId);
        }

        // 2️⃣ Récupérer tous les rendez-vous du patient
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        // 3️⃣ Trier par date et heure (du plus récent au plus ancien)
        appointments.sort((a1, a2) -> {
            int dateCompare = a2.getDate().compareTo(a1.getDate());
            if (dateCompare != 0) {
                return dateCompare;
            }
            return a2.getHeure().compareTo(a1.getHeure());
        });

        return appointments;
        // exemple de return [
        // { id: 5, doctorId: 3, date: 2025-10-25, heure: 14:00, statut: PLANIFIE },
        // { id: 3, doctorId: 5, date: 2025-10-20, heure: 09:00, statut: TERMINE },
        // { id: 1, doctorId: 5, date: 2025-10-15, heure: 11:30, statut: TERMINE }
        // ]
    }

    // Mettre à jour le statut d'un rendez-vous
    public Appointment updateAppointmentStatus(Long appointmentId, StatutRendezVous newStatut) {
        // 1️⃣ Récupérer le rendez-vous
        Optional<Appointment> optAppt = appointmentRepository.findById(appointmentId);
        if (optAppt.isEmpty()) {
            throw new RuntimeException("Rendez-vous non trouvé avec l'ID: " + appointmentId);
        }

        // 2️⃣ Mettre à jour le statut
        Appointment appointment = optAppt.get();
        appointment.setStatut(newStatut);

        // 3️⃣ Enregistrer les modifications
        Appointment updated = appointmentRepository.save(appointment);

        return updated;
        // exemple de return {
        // id: 1,
        // doctorId: 5,
        // patientId: 12,
        // date: 2025-10-20,
        // heure: 09:00,
        // type: CONSULTATION,
        // statut: TERMINE // ✅ statut mis à jour
        // }
    }

    // Annuler un rendez-vous
    public void cancelAppointment(Long appointmentId) {
        // 1️⃣ Récupérer le rendez-vous
        Optional<Appointment> optAppt = appointmentRepository.findById(appointmentId);
        if (optAppt.isEmpty()) {
            throw new RuntimeException("Rendez-vous non trouvé avec l'ID: " + appointmentId);
        }

        // 2️⃣ Mettre le statut à ANNULE
        Appointment appointment = optAppt.get();
        appointment.setStatut(StatutRendezVous.ANNULE);

        // 3️⃣ Enregistrer les modifications
        appointmentRepository.save(appointment);

        // Pas de return car la méthode est void
        // Le rendez-vous reste dans la base avec statut ANNULE
    }
}