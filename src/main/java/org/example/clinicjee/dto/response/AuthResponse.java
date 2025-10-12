package org.example.clinicjee.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clinicjee.domain.enums.Role;

public class AuthResponse {

    private String token;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    private String email;
    private Role role;

    @JsonProperty("expires_in")
    private Long expiresIn; // Secondes (ex: 86400 = 24h)

    // Constructeurs
    public AuthResponse() {}

    // Constructeur complet
    public AuthResponse(String token, Long userId, String fullName,
                        String email, Role role, Long expiresIn) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    // Constructeur simplifié (calcule automatiquement expiresIn = 24h)
    public AuthResponse(String token, Long userId, String fullName,
                        String email, Role role) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.expiresIn = 24 * 60 * 60L; // 24 heures en secondes
    }

    // Getters et Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
}