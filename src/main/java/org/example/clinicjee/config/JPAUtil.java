package org.example.clinicjee.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            System.out.println("🔄 Initializing EntityManagerFactory...");
            // Create EntityManagerFactory using persistence unit name from persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            System.out.println("✅ EntityManagerFactory created successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error creating EntityManagerFactory:");
            System.err.println("   Message: " + e.getMessage());
            System.err.println("   Class: " + e.getClass().getName());
            e.printStackTrace();
            // Ne pas lancer d'exception ici pour permettre à l'application de démarrer
            entityManagerFactory = null;
        }
    }

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized. Check logs for details.");
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("✅ EntityManagerFactory closed successfully!");
        }
    }
}