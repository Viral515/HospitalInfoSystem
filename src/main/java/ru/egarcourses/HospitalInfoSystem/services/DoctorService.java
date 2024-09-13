package ru.egarcourses.HospitalInfoSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    @Transactional
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Transactional
    public void update(int id, Doctor updatedDoctor) {
        updatedDoctor.setId(id);
        doctorRepository.save(updatedDoctor);
    }

    @Transactional
    public void delete(int id) {
        doctorRepository.deleteById(id);
    }
}
