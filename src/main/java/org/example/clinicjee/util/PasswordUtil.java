package org.example.clinicjee.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilitaire pour le hachage et la vérification des mots de passe
 * Utilise BCrypt pour un hachage sécurisé
 */
public class PasswordUtil {
    
    // Coût de hachage (entre 10-12 recommandé pour la production)
    private static final int HASH_ROUNDS = 12;
    
    /**
     * Hache un mot de passe en utilisant BCrypt
     * @param plainPassword Le mot de passe en clair
     * @return Le mot de passe haché
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(HASH_ROUNDS));
    }
    
    /**
     * Vérifie si un mot de passe en clair correspond au hash
     * @param plainPassword Le mot de passe en clair à vérifier
     * @param hashedPassword Le hash stocké en base
     * @return true si le mot de passe correspond, false sinon
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            // Log l'erreur si nécessaire
            System.err.println("Erreur lors de la vérification du mot de passe: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Vérifie si un mot de passe respecte les critères de sécurité
     * @param password Le mot de passe à valider
     * @return true si le mot de passe est valide
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        
        // Minimum 8 caractères
        if (password.length() < 8) {
            return false;
        }
        
        // Au moins une majuscule
        boolean hasUpperCase = false;
        // Au moins une minuscule
        boolean hasLowerCase = false;
        // Au moins un chiffre
        boolean hasDigit = false;
        // Au moins un caractère spécial
        boolean hasSpecialChar = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
}