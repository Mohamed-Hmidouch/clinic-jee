package org.example.clinicjee.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class PersistenceBootstrapListener implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            System.out.println("[JPA Bootstrap] Initializing EntityManagerFactory for PU 'default'...");
            emf = Persistence.createEntityManagerFactory("default");
            // Optionally store in context if needed by servlets
            sce.getServletContext().setAttribute("EMF", emf);
            System.out.println("[JPA Bootstrap] EntityManagerFactory initialized successfully.");
        } catch (Throwable t) {
            System.err.println("[JPA Bootstrap] Failed to initialize EntityManagerFactory: " + t.getMessage());
            t.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (emf != null && emf.isOpen()) {
                System.out.println("[JPA Bootstrap] Closing EntityManagerFactory...");
                emf.close();
                System.out.println("[JPA Bootstrap] EntityManagerFactory closed.");
            }
        } catch (Throwable t) {
            System.err.println("[JPA Bootstrap] Error while closing EntityManagerFactory: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
