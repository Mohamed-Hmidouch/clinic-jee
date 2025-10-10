package org.example.clinicjee.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Specialty: id, code, nom, description
 */
@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    private String code;
    private String nom;

    @OneToMany(mappedBy = "specialite", fetch = FetchType.LAZY)
    private List<Doctor> doctors = new ArrayList<>();

    public Specialty() {
    }

    public Specialty(Long id, Department department,String code, String nom) {
        this.id = id;
        this.department = department;
        this.code = code;
        this.nom = nom;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getDepartment() {return department;}

    public void setDepartment(Department department) {this.department = department;}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
