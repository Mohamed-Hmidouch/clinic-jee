package org.example.clinicjee.exception;

/**
 * Exception personnalisée pour les erreurs d'authentification
 * Respecte le principe de Single Responsibility
 */
public class AuthException extends RuntimeException {
    
    private final String errorCode;
    
    public AuthException(String message) {
        super(message);
        this.errorCode = "AUTH_ERROR";
    }
    
    public AuthException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public AuthException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "AUTH_ERROR";
    }
    
    public AuthException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    // Codes d'erreur standardisés
    public static class ErrorCodes {
        public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
        public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
        public static final String USER_INACTIVE = "USER_INACTIVE";
        public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";
        public static final String WEAK_PASSWORD = "WEAK_PASSWORD";
        public static final String INVALID_TOKEN = "INVALID_TOKEN";
        public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
        public static final String REGISTRATION_FAILED = "REGISTRATION_FAILED";
        public static final String INVALID_INPUT = "INVALID_INPUT";
    }

}