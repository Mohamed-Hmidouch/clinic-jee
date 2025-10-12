package org.example.clinicjee.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.example.clinicjee.domain.enums.Role;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Utilitaire pour la génération et validation des tokens JWT
 */
public class JwtUtil {
    
    // Clé secrète pour signer les tokens (À CHANGER EN PRODUCTION !)
    private static final String SECRET_KEY = "clinic-jee-super-secret-key-change-in-production-2024-very-long-key";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    // Durée de validité du token (24 heures)
    private static final long TOKEN_VALIDITY = 24 * 60 * 60; // 24h en secondes
    
    /**
     * Génère un token JWT pour un utilisateur
     * @param userId ID de l'utilisateur
     * @param email Email de l'utilisateur
     * @param role Rôle de l'utilisateur
     * @return Token JWT
     */
    public static String generateToken(Long userId, String email, Role role) {
        Instant now = Instant.now();
        Instant expiration = now.plus(TOKEN_VALIDITY, ChronoUnit.SECONDS);
        
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }
    
    /**
     * Extrait l'email du token
     * @param token Token JWT
     * @return Email de l'utilisateur
     */
    public static String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }
    
    /**
     * Extrait l'ID utilisateur du token
     * @param token Token JWT
     * @return ID de l'utilisateur
     */
    public static Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }
    
    /**
     * Extrait le rôle du token
     * @param token Token JWT
     * @return Rôle de l'utilisateur
     */
    public static Role getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String roleStr = claims.get("role", String.class);
        return Role.valueOf(roleStr);
    }
    
    /**
     * Vérifie si le token est expiré
     * @param token Token JWT
     * @return true si expiré, false sinon
     */
    public static boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaimsFromToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // En cas d'erreur, considérer comme expiré
        }
    }
    
    /**
     * Valide un token JWT
     * @param token Token à valider
     * @return true si valide, false sinon
     */
    public static boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token JWT invalide: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Extrait les claims du token
     * @param token Token JWT
     * @return Claims du token
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
     * Extrait le token du header Authorization
     * @param authHeader Header Authorization (Bearer token)
     * @return Token JWT sans le préfixe Bearer
     */
    public static String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Enlever "Bearer "
        }
        return null;
    }
    
    /**
     * Obtient la date d'expiration du token
     * @param token Token JWT
     * @return Date d'expiration
     */
    public static Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }
}