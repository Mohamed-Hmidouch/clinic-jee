package org.example.clinicjee.controller.web.patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.clinicjee.domain.Patient;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Contrôleur web pour le dashboard patient
 * Gère l'affichage du tableau de bord et les fonctionnalités patients
 */
@WebServlet(name = "PatientDashboardServlet", urlPatterns = {"/patient/dashboard", "/patient/*"})
public class PatientDashboardServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialisation des services et repositories sera faite plus tard
        // quand les méthodes seront pleinement implémentées
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        Patient patient = (Patient) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        try {
            switch (pathInfo != null ? pathInfo : "/dashboard") {
                case "/dashboard":
                    showDashboard(request, response, patient);
                    break;
                case "/appointments":
                    showAppointments(request, response, patient);
                    break;
                case "/book-appointment":
                    showBookAppointment(request, response);
                    break;
                case "/medical-history":
                    showMedicalHistory(request, response, patient);
                    break;
                default:
                    showDashboard(request, response, patient);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors du chargement de la page.");
            request.getRequestDispatcher("/WEB-INF/views/patient/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        Patient patient = (Patient) session.getAttribute("user");
        String action = request.getParameter("action");

        try {
            switch (action != null ? action : "") {
                case "book-appointment":
                    handleBookAppointment(request, response, patient);
                    break;
                case "cancel-appointment":
                    handleCancelAppointment(request, response, patient);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/patient/dashboard");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors du traitement de votre demande.");
            showDashboard(request, response, patient);
        }
    }

    /**
     * Affiche le dashboard principal du patient
     */
    private void showDashboard(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        // Pour l'instant, passer des données statiques
        // TODO: Récupérer les prochains rendez-vous du patient
        // List<Appointment> upcomingAppointments = appointmentService.findUpcomingAppointmentsByPatient(patient.getId());
        
        // TODO: Récupérer les statistiques du patient  
        // long totalAppointments = appointmentService.countAppointmentsByPatient(patient.getId());
        // long pendingAppointments = appointmentService.countAppointmentsByPatientAndStatus(
        //     patient.getId(), StatutRendezVous.PLANIFIE);
        
        // Préparer les données pour la vue
        request.setAttribute("patient", patient);
        // request.setAttribute("upcomingAppointments", upcomingAppointments);
        // request.setAttribute("totalAppointments", totalAppointments);
        // request.setAttribute("pendingAppointments", pendingAppointments);
        request.setAttribute("currentDate", LocalDate.now());
        
        request.getRequestDispatcher("/WEB-INF/views/patient/dashboard.jsp").forward(request, response);
    }

    /**
     * Affiche la liste des rendez-vous du patient
     */
    private void showAppointments(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        // TODO: Implémenter la récupération des rendez-vous
        // String statusFilter = request.getParameter("status");
        // List<Appointment> appointments;
        
        // if (statusFilter != null && !statusFilter.isEmpty()) {
        //     StatutRendezVous status = StatutRendezVous.valueOf(statusFilter);
        //     appointments = appointmentService.findAppointmentsByPatientAndStatus(patient.getId(), status);
        // } else {
        //     appointments = appointmentService.findAppointmentsByPatient(patient.getId());
        // }
        
        request.setAttribute("patient", patient);
        // request.setAttribute("appointments", appointments);
        // request.setAttribute("selectedStatus", statusFilter);
        
        request.getRequestDispatcher("/WEB-INF/views/patient/appointments.jsp").forward(request, response);
    }

    /**
     * Affiche le formulaire de prise de rendez-vous
     */
    private void showBookAppointment(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // TODO: Récupérer toutes les spécialités pour le formulaire
        // List<Specialty> specialties = specialtyRepository.findAll();
        
        // TODO: Si une spécialité est sélectionnée, récupérer les médecins de cette spécialité
        String specialtyIdParam = request.getParameter("specialty");
        // List<User> doctors = null;
        
        // if (specialtyIdParam != null && !specialtyIdParam.isEmpty()) {
        //     try {
        //         Long specialtyId = Long.parseLong(specialtyIdParam);
        //         doctors = userRepository.findByRole(Role.DOCTOR); // Filtrer par spécialité plus tard
        //     } catch (NumberFormatException e) {
        //         // Ignorer l'erreur de format
        //     }
        // }
        
        // request.setAttribute("specialties", specialties);
        // request.setAttribute("doctors", doctors);
        request.setAttribute("selectedSpecialty", specialtyIdParam);
        
        request.getRequestDispatcher("/WEB-INF/views/patient/book-appointment.jsp").forward(request, response);
    }

    /**
     * Affiche l'historique médical du patient
     */
    private void showMedicalHistory(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        // TODO: Récupérer l'historique des rendez-vous terminés
        // List<Appointment> completedAppointments = appointmentService.findAppointmentsByPatientAndStatus(
        //     patient.getId(), StatutRendezVous.TERMINE);
        
        request.setAttribute("patient", patient);
        // request.setAttribute("completedAppointments", completedAppointments);
        
        request.getRequestDispatcher("/WEB-INF/views/patient/medical-history.jsp").forward(request, response);
    }

    /**
     * Traite la demande de prise de rendez-vous
     */
    private void handleBookAppointment(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        try {
            String doctorIdParam = request.getParameter("doctorId");
            String dateParam = request.getParameter("date");
            String timeParam = request.getParameter("time");
            String dureeParam = request.getParameter("duree");
            
            if (doctorIdParam == null || dateParam == null || timeParam == null || dureeParam == null) {
                request.setAttribute("error", "Tous les champs sont obligatoires.");
                showBookAppointment(request, response);
                return;
            }
            
            // TODO: Implémenter la logique de création de rendez-vous
            // Long doctorId = Long.parseLong(doctorIdParam);
            // LocalDate date = LocalDate.parse(dateParam);
            // Integer duree = Integer.parseInt(dureeParam);
            
            // TODO: Implémenter la logique de création de rendez-vous
            // boolean success = appointmentService.createAppointment(patient.getId(), doctorId, date, time, duree);
            
            // Pour l'instant, simuler le succès
            boolean success = true;
            
            if (success) {
                request.setAttribute("success", "Rendez-vous créé avec succès!");
                response.sendRedirect(request.getContextPath() + "/patient/appointments");
            } else {
                request.setAttribute("error", "Impossible de créer le rendez-vous. Vérifiez la disponibilité.");
                showBookAppointment(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création du rendez-vous: " + e.getMessage());
            showBookAppointment(request, response);
        }
    }

    /**
     * Traite la demande d'annulation de rendez-vous
     */
    private void handleCancelAppointment(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        try {
            String appointmentIdParam = request.getParameter("appointmentId");
            
            if (appointmentIdParam == null) {
                request.setAttribute("error", "ID de rendez-vous manquant.");
                showAppointments(request, response, patient);
                return;
            }
            
            // TODO: Vérifier que le rendez-vous appartient bien au patient
            // TODO: Implémenter la logique d'annulation
            // Long appointmentId = Long.parseLong(appointmentIdParam);
            // boolean success = appointmentService.cancelAppointment(appointmentId, patient.getId());
            
            // Pour l'instant, simuler le succès
            boolean success = true;
            
            if (success) {
                request.setAttribute("success", "Rendez-vous annulé avec succès!");
            } else {
                request.setAttribute("error", "Impossible d'annuler le rendez-vous.");
            }
            
            showAppointments(request, response, patient);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'annulation: " + e.getMessage());
            showAppointments(request, response, patient);
        }
    }
}