package ru.egarcourses.HospitalInfoSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.repositories.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(int id) {
        Optional<Patient> foundPatient =  patientRepository.findById(id);

        return foundPatient.orElse(null);
    }

    @Transactional
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    @Transactional
    public void update(int id, Patient updatedPatient) {
        updatedPatient.setId(id);
        patientRepository.save(updatedPatient);
    }

    @Transactional
    public void delete(int id) {
        patientRepository.deleteById(id);
    }
}
