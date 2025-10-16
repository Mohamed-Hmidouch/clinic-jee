package org.example.clinicjee.service;

import org.example.clinicjee.domain.Specialty;
import org.example.clinicjee.repository.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService() {
        this.specialtyRepository = new SpecialtyRepository();
    }

    public Optional<Specialty> getSpecialtyById(Long id) {
        return specialtyRepository.findById(id);
    }

    public List<Specialty> getSpecialtiesByDepartment(Long departmentId) {
        return specialtyRepository.findByDepartmentId(departmentId);
    }
}
