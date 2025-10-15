package org.example.clinicjee.controller.api.patient;

import org.example.clinicjee.domain.Appointment;
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
        // Création d'un rendez-vous
        // attend JSON: { doctorId, patientId, date, heure, type }
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        Appointment appt = mapper.readValue(req.getReader(), Appointment.class);
        Appointment created = appointmentService.createAppointment(appt);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(mapper.writeValueAsString(created));
    }
}
