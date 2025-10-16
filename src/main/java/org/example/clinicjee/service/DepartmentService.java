package org.example.clinicjee.service;

import org.example.clinicjee.domain.Department;
import org.example.clinicjee.domain.Specialty;
import org.example.clinicjee.domain.Doctor;
import org.example.clinicjee.repository.DepartmentRepository;
import org.example.clinicjee.repository.SpecialtyRepository;
import org.example.clinicjee.repository.DoctorRepository;

import java.util.List;

public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorRepository doctorRepository;

    public DepartmentService() {
        this.departmentRepository = new DepartmentRepository();
        this.specialtyRepository = new SpecialtyRepository();
        this.doctorRepository = new DoctorRepository();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Specialty> getSpecialtiesByDepartment(Long departmentId) {
        return specialtyRepository.findByDepartmentId(departmentId);
    }

    public List<Doctor> getDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findBySpecialtyId(specialtyId);
    }
}
