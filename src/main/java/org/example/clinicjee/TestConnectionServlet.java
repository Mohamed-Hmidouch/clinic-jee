package org.example.clinicjee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-connection")
public class TestConnectionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h1>Test de connexion</h1>");

        try (EntityManager em = org.example.clinicjee.config.JPAUtil.getEntityManager()) {

            // Test simple query
            Object result = em.createNativeQuery("SELECT version()").getSingleResult();

            out.println("<p style='color:green;'>✓ Connexion réussie!</p>");
            out.println("<p>Version PostgreSQL: " + result + "</p>");

        } catch (Exception e) {
            out.println("<p style='color:red;'>✗ Erreur de connexion:</p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace();

        }
        
        out.println("</body></html>");
    }
}
