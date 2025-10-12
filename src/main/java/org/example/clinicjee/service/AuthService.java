package org.example.clinicjee.service;

import org.example.clinicjee.dto.request.LoginRequest;
import org.example.clinicjee.dto.request.RegisterRequest;
import org.example.clinicjee.dto.response.AuthResponse;
import org.example.clinicjee.repository.UserRepository;
import org.example.clinicjee.repository.SpecialtyRepository;
import org.example.clinicjee.repository.impl.UserRepositoryImpl;
import org.example.clinicjee.repository.impl.SpecialtyRepositoryImpl;
import org.example.clinicjee.util.PasswordUtil;
import org.example.clinicjee.util.JwtUtil;
import org.example.clinicjee.domain.*;
import org.example.clinicjee.domain.enums.Role;
import org.example.clinicjee.exception.AuthException;

import java.util.Optional;

/**
 * Service d'authentification
 * Gère l'inscription (register) et la connexion (login)
 * Respecte le Single Responsibility Principle
 */
public class AuthService {

    private final UserRepository userRepository;
    private final SpecialtyRepository specialtyRepository;

    public AuthService() {
        this.userRepository = new UserRepositoryImpl();
        this.specialtyRepository = new SpecialtyRepositoryImpl();
    }

    /**
     * Inscription d'un nouvel utilisateur
     */
    public AuthResponse register(RegisterRequest request) {
        // 1. Validation email
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new AuthException("Email déjà utilisé", AuthException.ErrorCodes.EMAIL_ALREADY_EXISTS);
        }

        // 2. Validation mot de passe
        if (!PasswordUtil.isValidPassword(request.getPassword())) {
            throw new AuthException("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial", 
                                  AuthException.ErrorCodes.WEAK_PASSWORD);
        }

        // 3. Hasher le mot de passe
        String hashedPassword = PasswordUtil.hashPassword(request.getPassword());

        // 4. Créer l'entité selon le rôle
        User user = createUserByRole(request, hashedPassword);

        // 5. Sauvegarder en base
        User savedUser = userRepository.save(user);

        // 6. Générer le token JWT
        String token = JwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());

        // 7. Construire la réponse
        return new AuthResponse(
            token,
            savedUser.getId(),
            savedUser.getFullName(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
    }

    /**
     * Connexion d'un utilisateur existant
     */
    public AuthResponse login(LoginRequest request) {
        // 1. Trouver l'utilisateur par email
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new AuthException("Email ou mot de passe incorrect", AuthException.ErrorCodes.INVALID_CREDENTIALS);
        }

        User user = userOpt.get();

        // 2. Vérifier que le compte est actif
        if (!user.getActif()) {
            throw new AuthException("Compte désactivé. Contactez l'administrateur", AuthException.ErrorCodes.USER_INACTIVE);
        }

        // 3. Vérifier le mot de passe
        boolean passwordMatch = PasswordUtil.verifyPassword(request.getPassword(), user.getPasswordHash());
        if (!passwordMatch) {
            throw new AuthException("Email ou mot de passe incorrect", AuthException.ErrorCodes.INVALID_CREDENTIALS);
        }

        // 4. Générer le token JWT
        String token = JwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        // 5. Construire la réponse
        return new AuthResponse(
            token,
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole()
        );
    }

    /**
     * Valide un token JWT et retourne l'utilisateur
     */
    public User validateToken(String token) {
        // 1. Valider le token
        if (!JwtUtil.validateToken(token)) {
            throw new AuthException("Token invalide", AuthException.ErrorCodes.INVALID_TOKEN);
        }

        // 2. Extraire l'ID utilisateur
        Long userId = JwtUtil.getUserIdFromToken(token);

        // 3. Récupérer l'utilisateur depuis la DB
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new AuthException("Utilisateur non trouvé", AuthException.ErrorCodes.USER_NOT_FOUND);
        }

        User user = userOpt.get();

        // 4. Vérifier que le compte est toujours actif
        if (!user.getActif()) {
            throw new AuthException("Compte désactivé", AuthException.ErrorCodes.USER_INACTIVE);
        }

        return user;
    }

    /**
     * Récupère les informations de l'utilisateur connecté
     */
    public User getCurrentUser(String token) {
        return validateToken(token);
    }

    /**
     * Crée l'entité User appropriée selon le rôle
     */
    private User createUserByRole(RegisterRequest request, String hashedPassword) {
        switch (request.getRole()) {
            case PATIENT:
                Patient patient = new Patient(
                    request.getFullName(),
                    request.getEmail(),
                    hashedPassword,
                    request.getCin(),
                    request.getNaissance()
                );
                patient.setActif(true);
                return patient;

            case DOCTEUR:
                // Récupération de la spécialité
                Specialty specialty = null;
                if (request.getSpecialtyId() != null) {
                    specialty = specialtyRepository.findById(request.getSpecialtyId())
                        .orElseThrow(() -> new AuthException(
                            "Spécialité non trouvée avec l'ID: " + request.getSpecialtyId(),
                            AuthException.ErrorCodes.REGISTRATION_FAILED
                        ));
                }
                
                Doctor doctor = new Doctor(
                    request.getFullName(),
                    request.getEmail(),
                    hashedPassword,
                    request.getMatricule(),
                    request.getTitre(),
                    specialty
                );
                doctor.setActif(true);
                return doctor;
            case STAFF:
                User user = new User(
                    null,
                    request.getFullName(),
                    request.getEmail(),
                    hashedPassword,
                    request.getRole()
                );
                user.setActif(true);
                return user;

            default:
                throw new AuthException("Rôle non supporté", AuthException.ErrorCodes.REGISTRATION_FAILED);
        }
    }
}