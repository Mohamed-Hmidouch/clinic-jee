package org.example.clinicjee.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.User;
import org.example.clinicjee.domain.enums.Role;
import org.example.clinicjee.repository.UserRepository;

import java.util.List;
import java.util.Optional;


public class UserRepositoryImpl implements UserRepository {
    
    // ========== CREATE / UPDATE ==========
    @Override
    public User save(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            if (user.getId() == null) {
                em.persist(user);
            } else {
                user = em.merge(user);
            }
            
            em.getTransaction().commit();
            return user;
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde de l'utilisateur", e);
        } finally {
            em.close();
        }
    }
    
    // ========== READ ==========
    @Override
    public Optional<User> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
            
        } finally {
            em.close();
        }
    }
    @Override
    public Optional<User> findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", 
                User.class
            );
            query.setParameter("email", email);
            
            try {
                return Optional.of(query.getSingleResult());
            } catch (NoResultException e) {
                return Optional.empty();
            }
            
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findByRole(Role role) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.role = :role",
                User.class
            );
            query.setParameter("role", role);
            
            return query.getResultList();
            
        } finally {
            em.close();
        }
    }
    @Override
    public List<User> findByRoleAndActif(Role role, Boolean actif) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.role = :role AND u.actif = :actif ORDER BY u.fullName",
                User.class
            );
            query.setParameter("role", role);
            query.setParameter("actif", actif);
            
            return query.getResultList();
            
        } finally {
            em.close();
        }
    }
    @Override
    public List<User> findAll(int page, int size) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u ORDER BY u.id",
                User.class
            );
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            
            return query.getResultList();
            
        } finally {
            em.close();
        }
    }
    
    // ========== SOFT DELETE ==========
    @Override
    public void softDelete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            User user = em.find(User.class, id);
            
            if (user != null) {
                user.setActif(false);
            } else {
                throw new RuntimeException("User avec ID " + id + " non trouvé");
            }
            
            em.getTransaction().commit();
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la désactivation de l'utilisateur", e);
        } finally {
            em.close();
        }
    }
}