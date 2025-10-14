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

@WebServlet("/api/auth/*")
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
            String pathInfo = request.getPathInfo();
            
            switch (pathInfo) {
                case "/register":
                    handleRegister(request, response);
                    break;
                case "/login":
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
    
    /**
     * Gère l'inscription - POST /api/auth/register
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // 1. Lire le JSON depuis le body
        RegisterRequest registerRequest = readJsonFromRequest(request, RegisterRequest.class);
        
        // 2. Valider les données requises
        validateRegisterRequest(registerRequest);
        
        // 3. Appeler le service
        AuthResponse authResponse = authService.register(registerRequest);
        
        // 4. Envoyer la réponse
        response.setStatus(HttpServletResponse.SC_CREATED);
        sendJsonResponse(response, authResponse);
    }
    
    /**
     * Gère la connexion - POST /api/auth/login
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // 1. Lire le JSON depuis le body
        LoginRequest loginRequest = readJsonFromRequest(request, LoginRequest.class);
        
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

        // 5. Envoyer la réponse
        response.setStatus(HttpServletResponse.SC_OK);
        sendJsonResponse(response, authResponse);
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