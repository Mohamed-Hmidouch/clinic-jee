package org.example.clinicjee.service;

import org.example.clinicjee.domain.Doctor;
import org.example.clinicjee.repository.DoctorRepository;

import java.util.List;
import java.util.Optional;

public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService() {
        this.doctorRepository = new DoctorRepository();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> getDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findBySpecialtyId(specialtyId);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
