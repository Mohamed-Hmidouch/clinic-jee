package org.example.clinicjee.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clinicjee.domain.enums.*;
import java.time.LocalDate;

public class RegisterRequest {

    // ========== Commun à tous ==========
    @JsonProperty("full_name")
    private String fullName;

    private String email;
    private String password;

    @JsonProperty("role")
    private Role role;  // Enum OK ✅

    // ========== Spécifique Patient ==========
    private String cin;

    @JsonProperty("date_naissance")
    private LocalDate naissance;

    // ========== Spécifique Doctor ==========
    private String matricule;
    private String titre;

    @JsonProperty("specialty_id")  // ← Juste l'ID !
    private Long specialtyId;  // ✅ BON

    // Constructeurs
    public RegisterRequest() {}

    // Getters et Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // Patient
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public LocalDate getNaissance() { return naissance; }
    public void setNaissance(LocalDate naissance) { this.naissance = naissance; }

    // Doctor
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public Long getSpecialtyId() { return specialtyId; }
    public void setSpecialtyId(Long specialtyId) { this.specialtyId = specialtyId; }
}