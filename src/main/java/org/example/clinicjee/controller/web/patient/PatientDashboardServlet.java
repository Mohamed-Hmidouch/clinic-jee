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
import java.util.List;
import java.util.Map;

@WebServlet(name = "PatientDashboardServlet", urlPatterns = {"/patient/dashboard", "/patient/*"})
public class PatientDashboardServlet extends HttpServlet {

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
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
                showDashboard(request, response, patient);
            } else if (pathInfo.equals("/book-appointment")) {
                showBookAppointment(request, response, patient);
            } else {
                showDashboard(request, response, patient);
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

        // Toutes les actions POST sont maintenant gérées par l'API
        response.sendRedirect(request.getContextPath() + "/patient/dashboard");
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        // Passer uniquement les informations du patient
        // Les données réelles (rendez-vous, stats) seront chargées via l'API
        request.setAttribute("patient", patient);
        request.setAttribute("currentDate", LocalDate.now());
        
        request.getRequestDispatcher("/WEB-INF/views/patient/dashboard.jsp").forward(request, response);
    }

    private void showBookAppointment(HttpServletRequest request, HttpServletResponse response, Patient patient) 
            throws ServletException, IOException {
        
        // Page de réservation - les données seront chargées via l'API
        request.setAttribute("patient", patient);
        request.getRequestDispatcher("/WEB-INF/views/patient/book-appointment.jsp").forward(request, response);
    }
}