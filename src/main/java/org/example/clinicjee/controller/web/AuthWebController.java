package org.example.clinicjee.controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.clinicjee.domain.User;
import org.example.clinicjee.domain.enums.Role;
import org.example.clinicjee.dto.request.RegisterRequest;
import org.example.clinicjee.dto.response.AuthResponse;
import org.example.clinicjee.service.AuthService;


import java.io.IOException;

/**
 * Contrôleur web pour les pages d'authentification
 * Gère l'affichage des formulaires de connexion/inscription et le traitement
 */
// Deprecated: servlet annotation removed to avoid duplicate mappings. AuthServlet handles auth routes now.
public class AuthWebController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/auth/login":
                showLoginForm(request, response);
                break;
            case "/auth/register":
                showRegisterForm(request, response);
                break;
            case "/auth/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/auth/register":
                handleRegister(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    /**
     * Affiche le formulaire d'inscription
     */
    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si l'utilisateur est déjà connecté, rediriger vers son dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectToDashboard(response, user.getRole());
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    /**
     * Affiche le formulaire de connexion
     */
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si l'utilisateur est déjà connecté, rediriger vers son dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectToDashboard(response, user.getRole());
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    // Login is handled by AuthLoginPageServlet (GET) and AuthServlet (/api/auth/login) (POST)

    /**
     * Traite l'inscription d'un nouvel utilisateur
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String roleParam = request.getParameter("role");
            
            // Validation des paramètres
            if (nom == null || nom.trim().isEmpty() ||
                prenom == null || prenom.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                roleParam == null || roleParam.trim().isEmpty()) {
                
                request.setAttribute("error", "Tous les champs sont obligatoires.");
                preserveFormData(request, nom, prenom, email, roleParam);
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }
            
            // Validation du mot de passe
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Les mots de passe ne correspondent pas.");
                preserveFormData(request, nom, prenom, email, roleParam);
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }
            
            // Conversion du rôle
            Role role;
            try {
                role = Role.valueOf(roleParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Rôle invalide.");
                preserveFormData(request, nom, prenom, email, roleParam);
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }
            
            // Tentative d'inscription
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setFullName(nom.trim() + " " + prenom.trim());
            registerRequest.setEmail(email.trim());
            registerRequest.setPassword(password);
            registerRequest.setRole(role);
            
            AuthResponse authResponse = authService.register(registerRequest);
            
            if (authResponse != null && authResponse.getToken() != null) {
                // Inscription réussie - rediriger vers la page de connexion avec message de succès
                request.setAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
                request.setAttribute("email", email); // Pré-remplir l'email
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "L'inscription a échoué. Cet email existe peut-être déjà.");
                preserveFormData(request, nom, prenom, email, roleParam);
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors de l'inscription.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }

    /**
     * Traite la déconnexion
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Rediriger vers la page d'accueil avec message de succès
        response.sendRedirect(request.getContextPath() + "/?logout=success");
    }

    /**
     * Redirige vers le dashboard approprié selon le rôle
     */
    private void redirectToDashboard(HttpServletResponse response, Role role) throws IOException {
        String dashboardPath;
        
        switch (role) {
            case ADMIN:
                dashboardPath = "/admin/dashboard";
                break;
            case DOCTEUR:
                dashboardPath = "/doctor/dashboard";
                break;
            case PATIENT:
                dashboardPath = "/patient/dashboard";
                break;
            case STAFF:
                dashboardPath = "/staff/dashboard";
                break;
            default:
                dashboardPath = "/";
                break;
        }
        
        response.sendRedirect(dashboardPath);
    }

    /**
     * Préserve les données du formulaire en cas d'erreur
     */
    private void preserveFormData(HttpServletRequest request, String nom, String prenom, String email, String role) {
        request.setAttribute("nom", nom);
        request.setAttribute("prenom", prenom);
        request.setAttribute("email", email);
        request.setAttribute("role", role);
    }
}