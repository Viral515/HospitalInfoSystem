package ru.egarcourses.HospitalInfoSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.repositories.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    public Specialty findById(int id) {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        return specialty.orElse(null);
    }

    public Specialty findByName(String name) {
        return specialtyRepository.findByName(name);
    }

    @Transactional
    public void save(Specialty specialty) {
        specialtyRepository.save(specialty);
    }

    @Transactional
    public void update(int id, Specialty updatedSpecialty) {
        updatedSpecialty.setId(id);
        specialtyRepository.save(updatedSpecialty);
    }

    @Transactional
    public void delete(int id) {
        specialtyRepository.deleteById(id);
    }
}
