package org.example.clinicjee.repository;

import org.example.clinicjee.domain.User;
import org.example.clinicjee.domain.enums.Role;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des entités User UNIQUEMENT
 * Respecte le Single Responsibility Principle
 */
public interface UserRepository {
    
    // ========== CORE USER OPERATIONS ==========
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll(int page, int size);
    
    // ========== USER BUSINESS LOGIC ==========
    List<User> findByRole(Role role);
    List<User> findByRoleAndActif(Role role, Boolean actif);
    void softDelete(Long id);
}
