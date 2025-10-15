package org.example.clinicjee.controller.api.patient;

import org.example.clinicjee.domain.Appointment;
import org.example.clinicjee.domain.Doctor;
import org.example.clinicjee.domain.Patient;
import org.example.clinicjee.dto.response.CreneauDTO;
import org.example.clinicjee.service.AppointmentService;
import org.example.clinicjee.repository.AppointmentRepository;
import org.example.clinicjee.repository.PatientRepository;
import org.example.clinicjee.repository.AvailabilityRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/api/patient/appointments")
public class PatientAppointmentServlet extends HttpServlet {
    private final AppointmentService appointmentService;

    public PatientAppointmentServlet() {
        // Instantiation directe du service avec ses dépendances
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        PatientRepository patientRepository = new PatientRepository();
        AvailabilityRepository availabilityRepository = new AvailabilityRepository();
        this.appointmentService = new AppointmentService(appointmentRepository, patientRepository, availabilityRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("creneauxDisponibles".equals(action)) {
            // ?action=creneauxDisponibles&doctorId=...&date=YYYY-MM-DD
            try {
                Long doctorId = Long.valueOf(req.getParameter("doctorId"));
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                List<CreneauDTO> creneaux = appointmentService.getCreneauxDisponibles(doctorId, date);
                
                // Construire JSON manuellement pour compatibilité
                StringBuilder json = new StringBuilder("{\"success\": true, \"availabilities\": [");
                boolean first = true;
                for (CreneauDTO c : creneaux) {
                    if (c.isDisponible()) {
                        if (!first) json.append(",");
                        json.append("{\"heure\": \"").append(c.getHeure()).append("\", \"disponible\": true}");
                        first = false;
                    }
                }
                json.append("]}");
                
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json.toString());
            } catch (Exception e) {
                resp.setContentType("application/json");
                resp.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
            }
        } else if ("byPatient".equals(action)) {
            // ?action=byPatient&patientId=...
            try {
                Long patientId = Long.valueOf(req.getParameter("patientId"));
                List<Appointment> appts = appointmentService.getAppointmentsByPatient(patientId);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(appts));
            } catch (Exception e) {
                resp.setContentType("application/json");
                resp.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // DEBUG: Log l'entrée dans doPost
        System.out.println("========================================");
        System.out.println("[DEBUG SERVLET POST] Requête POST reçue");
        System.out.println("[DEBUG SERVLET POST] URL: " + req.getRequestURL());
        System.out.println("[DEBUG SERVLET POST] Query String: " + req.getQueryString());
        
        String action = req.getParameter("action");
        System.out.println("[DEBUG SERVLET POST] Action: " + action);
        
        if ("create".equals(action)) {
            System.out.println("[DEBUG SERVLET POST] Action 'create' détectée");
            
            // Création simple d'un rendez-vous avec paramètres
            // ?action=create&doctorId=X&patientId=Y&date=YYYY-MM-DD&heure=HH:MM&type=CONSULTATION
            try {
                String doctorIdStr = req.getParameter("doctorId");
                String patientIdStr = req.getParameter("patientId");
                String dateStr = req.getParameter("date");
                String heureStr = req.getParameter("heure");
                String typeStr = req.getParameter("type");
                
                System.out.println("[DEBUG SERVLET POST] Paramètres reçus:");
                System.out.println("  - doctorId: " + doctorIdStr);
                System.out.println("  - patientId: " + patientIdStr);
                System.out.println("  - date: " + dateStr);
                System.out.println("  - heure: " + heureStr);
                System.out.println("  - type: " + typeStr);
                
                Long doctorId = Long.valueOf(doctorIdStr);
                Long patientId = Long.valueOf(patientIdStr);
                LocalDate date = LocalDate.parse(dateStr);
                LocalTime heure = LocalTime.parse(heureStr);
                
                System.out.println("[DEBUG SERVLET POST] Paramètres parsés avec succès");
                
                // Créer l'objet Appointment
                Appointment appt = new Appointment();
                
                // Récupérer le docteur
                Doctor doctor = new Doctor();
                doctor.setId(doctorId);
                appt.setDoctor(doctor);
                
                // Récupérer le patient
                Patient patient = new Patient();
                patient.setId(patientId);
                appt.setPatient(patient);
                
                appt.setDate(date);
                appt.setHeure(heure);
                appt.setType(org.example.clinicjee.domain.enums.TypeRendezVous.valueOf(typeStr != null ? typeStr : "CONSULTATION"));
                appt.setStatut(org.example.clinicjee.domain.enums.StatutRendezVous.PLANIFIE);
                
                // La durée est toujours 30 minutes pour tous les types
                appt.setDuree(30);
                
                System.out.println("[DEBUG SERVLET POST] Objet Appointment créé:");
                System.out.println("  - Doctor ID: " + appt.getDoctor().getId());
                System.out.println("  - Patient ID: " + appt.getPatient().getId());
                System.out.println("  - Date: " + appt.getDate());
                System.out.println("  - Heure: " + appt.getHeure());
                System.out.println("  - Type: " + appt.getType());
                System.out.println("  - Statut: " + appt.getStatut());
                System.out.println("  - Durée: " + appt.getDuree() + " minutes");
                
                System.out.println("[DEBUG SERVLET POST] Appel du service createAppointment...");
                Appointment created = appointmentService.createAppointment(appt);
                
                System.out.println("[DEBUG SERVLET POST] Rendez-vous créé avec succès!");
                System.out.println("[DEBUG SERVLET POST] ID du rendez-vous: " + created.getId());
                
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String jsonResponse = "{\"success\": true, \"appointmentId\": " + created.getId() + "}";
                System.out.println("[DEBUG SERVLET POST] Réponse JSON: " + jsonResponse);
                resp.getWriter().write(jsonResponse);
                
                System.out.println("[DEBUG SERVLET POST] Réponse envoyée avec succès");
                System.out.println("========================================");
            } catch (Exception e) {
                System.err.println("[DEBUG SERVLET POST] ERREUR lors de la création:");
                e.printStackTrace();
                
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String errorMsg = e.getMessage() != null ? e.getMessage() : e.getClass().getName();
                String jsonError = "{\"success\": false, \"error\": \"" + errorMsg.replace("\"", "'") + "\"}";
                System.out.println("[DEBUG SERVLET POST] Réponse d'erreur: " + jsonError);
                resp.getWriter().write(jsonError);
                
                System.out.println("========================================");
            }
        } else {
            System.out.println("[DEBUG SERVLET POST] Action '" + action + "' non reconnue, utilisation de l'ancienne méthode JSON");
            
            // Ancienne méthode - création avec JSON
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Appointment appt = mapper.readValue(req.getReader(), Appointment.class);
            Appointment created = appointmentService.createAppointment(appt);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(mapper.writeValueAsString(created));
            
            System.out.println("========================================");
        }
    }
}
