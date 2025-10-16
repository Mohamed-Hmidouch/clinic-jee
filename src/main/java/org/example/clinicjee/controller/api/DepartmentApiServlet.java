package org.example.clinicjee.controller.api;

import org.example.clinicjee.domain.Department;
import org.example.clinicjee.domain.Specialty;
import org.example.clinicjee.domain.Doctor;
import org.example.clinicjee.service.DepartmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/departments")
public class DepartmentApiServlet extends HttpServlet {
    private final DepartmentService departmentService;

    public DepartmentApiServlet() {
        this.departmentService = new DepartmentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            if ("list".equals(action)) {
                // Liste de tous les départements
                List<Department> departments = departmentService.getAllDepartments();
                String json = buildDepartmentsJson(departments);
                resp.getWriter().write(json);
                
            } else if ("specialties".equals(action)) {
                // Liste des spécialités d'un département
                Long departmentId = Long.valueOf(req.getParameter("departmentId"));
                List<Specialty> specialties = departmentService.getSpecialtiesByDepartment(departmentId);
                String json = buildSpecialtiesJson(specialties);
                resp.getWriter().write(json);
                
            } else if ("doctors".equals(action)) {
                // Liste des médecins d'une spécialité
                Long specialtyId = Long.valueOf(req.getParameter("specialtyId"));
                List<Doctor> doctors = departmentService.getDoctorsBySpecialty(specialtyId);
                String json = buildDoctorsJson(doctors);
                resp.getWriter().write(json);
                
            } else {
                resp.getWriter().write("{\"success\": false, \"error\": \"Action inconnue\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private String buildDepartmentsJson(List<Department> departments) {
        StringBuilder json = new StringBuilder("{\"success\": true, \"departments\": [");
        boolean first = true;
        for (Department dept : departments) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\": ").append(dept.getId()).append(",");
            json.append("\"nom\": \"").append(escapeJson(dept.getNom())).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]}");
        return json.toString();
    }
    
    private String buildSpecialtiesJson(List<Specialty> specialties) {
        StringBuilder json = new StringBuilder("{\"success\": true, \"specialties\": [");
        boolean first = true;
        for (Specialty spec : specialties) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\": ").append(spec.getId()).append(",");
            json.append("\"nom\": \"").append(escapeJson(spec.getNom())).append("\",");
            json.append("\"code\": \"").append(escapeJson(spec.getCode())).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]}");
        return json.toString();
    }
    
    private String buildDoctorsJson(List<Doctor> doctors) {
        StringBuilder json = new StringBuilder("{\"success\": true, \"doctors\": [");
        boolean first = true;
        for (Doctor doctor : doctors) {
            if (!first) json.append(",");
            json.append("{");
            json.append("\"id\": ").append(doctor.getId()).append(",");
            json.append("\"nom\": \"").append(escapeJson(doctor.getFullName())).append("\",");
            json.append("\"titre\": \"").append(escapeJson(doctor.getTitre() != null ? doctor.getTitre() : "Dr.")).append("\"");
            json.append("}");
            first = false;
        }
        json.append("]}");
        return json.toString();
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
