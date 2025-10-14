package org.example.clinicjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.example.clinicjee.dto.request.LoginRequest;
import org.example.clinicjee.dto.request.RegisterRequest;
import org.example.clinicjee.dto.response.AuthResponse;
import org.example.clinicjee.service.AuthService;
import org.example.clinicjee.exception.AuthException;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/api/auth/*", "/auth/*"})
public class AuthServlet extends HttpServlet {
    
    private AuthService authService;
    private ObjectMapper objectMapper;
    
    @Override
    public void init() throws ServletException {
        this.authService = new AuthService();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Support LocalDate
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configuration commune des réponses
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // CORS pour développement
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        try {
            // Build a full path that works for both /api/auth/* and /auth/* mappings
            String fullPath = request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");
            switch (fullPath) {
                case "/api/auth/register":
                case "/auth/register":
                    handleRegister(request, response);
                    break;
                case "/api/auth/login":
                case "/auth/login":
                    handleLogin(request, response);
                    break;
                default:
                    sendErrorResponse(response, 404, "Endpoint non trouvé");
                    break;
            }
            
        } catch (AuthException e) {
            sendErrorResponse(response, 400, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, 500, "Erreur interne du serveur");
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Gestion CORS pour les requêtes préflight
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Serve web pages for GET /auth/login, /auth/register and handle logout
        String fullPath = request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");

        switch (fullPath) {
            case "/auth/login":
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                return;
            case "/auth/register":
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            case "/auth/logout":
                // invalidate session and redirect to home
                jakarta.servlet.http.HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();
                response.sendRedirect(request.getContextPath() + "/?logout=success");
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }
    }
    
    /**
     * Gère l'inscription - POST /api/auth/register
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        // Support both JSON API and traditional form POST
        RegisterRequest registerRequest;
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            // 1. Lire le JSON depuis le body
            registerRequest = readJsonFromRequest(request, RegisterRequest.class);
        } else {
            // Build from form params
            registerRequest = new RegisterRequest();
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String roleParam = request.getParameter("role");
            String fullName = "";
            if (nom != null) fullName += nom + " ";
            if (prenom != null) fullName += prenom;
            registerRequest.setFullName(fullName.trim());
            registerRequest.setEmail(email);
            registerRequest.setPassword(password);
            try {
                registerRequest.setRole(org.example.clinicjee.domain.enums.Role.valueOf(roleParam.toUpperCase()));
            } catch (Exception e) {
                registerRequest.setRole(null);
            }
        }

        // 2. Valider les données requises
        validateRegisterRequest(registerRequest);

        // 3. Appeler le service
        AuthResponse authResponse = authService.register(registerRequest);

        // 4. If request was from form (non-json) forward to login page, else return JSON
        if (contentType != null && contentType.contains("application/json")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            sendJsonResponse(response, authResponse);
        } else {
            if (authResponse != null && authResponse.getToken() != null) {
                request.setAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
                request.setAttribute("email", registerRequest.getEmail());
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "L'inscription a échoué. Cet email existe peut-être déjà.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }
        }
    }
    
    /**
     * Gère la connexion - POST /api/auth/login
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        // Support JSON API login and simple form POSTs
        String contentType = request.getContentType();
        LoginRequest loginRequest;
        if (contentType != null && contentType.contains("application/json")) {
            loginRequest = readJsonFromRequest(request, LoginRequest.class);
        } else {
            loginRequest = new LoginRequest();
            loginRequest.setEmail(request.getParameter("email"));
            loginRequest.setPassword(request.getParameter("password"));
        }

        // 2. Valider les données requises
        validateLoginRequest(loginRequest);

        // 3. Appeler le service
        AuthResponse authResponse = authService.login(loginRequest);

        // 4. Créer une session côté serveur pour les utilisateurs web (permet la redirection vers /patient/dashboard)
        try {
            // Récupérer l'utilisateur associé au token et le stocker en session
            org.example.clinicjee.domain.User currentUser = authService.getCurrentUser(authResponse.getToken());
            jakarta.servlet.http.HttpSession session = request.getSession(true);
            session.setAttribute("user", currentUser);
            session.setAttribute("userId", currentUser.getId());
            session.setAttribute("userRole", currentUser.getRole() != null ? currentUser.getRole().toString() : null);
        } catch (Exception e) {
            // Si la création de session échoue, on continue quand même et on renvoie la réponse API
            e.printStackTrace();
        }

        // 5. Respond either JSON or redirect
        if (contentType != null && contentType.contains("application/json")) {
            response.setStatus(HttpServletResponse.SC_OK);
            sendJsonResponse(response, authResponse);
        } else {
            // If this was a form POST (web), redirect to dashboard based on role
            org.example.clinicjee.domain.User currentUser = authService.getCurrentUser(authResponse.getToken());
            if (currentUser != null && currentUser.getRole() != null) {
                String roleName = currentUser.getRole().toString();
                switch (roleName) {
                    case "ADMIN":
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                        break;
                    case "DOCTEUR":
                        response.sendRedirect(request.getContextPath() + "/doctor/dashboard");
                        break;
                    case "PATIENT":
                        response.sendRedirect(request.getContextPath() + "/patient/dashboard");
                        break;
                    case "STAFF":
                        response.sendRedirect(request.getContextPath() + "/staff/dashboard");
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/");
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
    
    /**
     * Lit et désérialise le JSON depuis le body de la requête
     */
    private <T> T readJsonFromRequest(HttpServletRequest request, Class<T> valueType) 
            throws IOException {
        
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }
        
        String jsonString = jsonBuilder.toString();
        if (jsonString.trim().isEmpty()) {
            throw new AuthException("Corps de la requête vide", AuthException.ErrorCodes.INVALID_INPUT);
        }
        
        return objectMapper.readValue(jsonString, valueType);
    }
    
    /**
     * Envoie une réponse JSON de succès
     */
    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("data", data);
        
        String jsonResponse = objectMapper.writeValueAsString(responseBody);
        response.getWriter().write(jsonResponse);
    }
    
    /**
     * Envoie une réponse JSON d'erreur
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) 
            throws IOException {
        
        response.setStatus(statusCode);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", message);
        errorResponse.put("timestamp", System.currentTimeMillis());
        
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
    
    /**
     * Valide les données d'inscription
     */
    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new AuthException("Le nom complet est obligatoire", AuthException.ErrorCodes.INVALID_INPUT);
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new AuthException("L'email est obligatoire", AuthException.ErrorCodes.INVALID_INPUT);
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new AuthException("Le mot de passe est obligatoire", AuthException.ErrorCodes.INVALID_INPUT);
        }
        if (request.getRole() == null) {
            throw new AuthException("Le rôle est obligatoire", AuthException.ErrorCodes.INVALID_INPUT);
        }
        
        // Validation email format
        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new AuthException("Format d'email invalide", AuthException.ErrorCodes.INVALID_INPUT);
        }
    }
    
    /**
     * Valide les données de connexion
     */
    private void validateLoginRequest(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new AuthException("L'email est obligatoire", AuthException.ErrorCodes.INVALID_INPUT);
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new AuthException("Le mot de passe est obligatoire", AuthException.ErrorCodes.INVALID_INPUT);
        }
    }
}