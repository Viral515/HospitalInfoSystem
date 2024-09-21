package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.repositories.PatientRepository;
import ru.egarcourses.HospitalInfoSystem.services.PatientService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, MappingUtils mappingUtils) {
        this.patientRepository = patientRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    public List<PatientDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            throw new NotFoundException("Patients not found");
        }
        return patientRepository.findAll().stream().map(mappingUtils::mapToPatientDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) {
        Optional<Patient> foundPatient = patientRepository.findById(id);
        if (!foundPatient.isPresent()) {
            throw new NotFoundException("Patient not found");
        }
        return mappingUtils.mapToPatientDTO(foundPatient.get());
    }

    @Transactional
    @Override
    public void save(PatientDTO patientDTO) {
        patientRepository.save(mappingUtils.mapToPatient(patientDTO));
    }

    @Transactional
    @Override
    public void update(Long id, PatientDTO updatedPatientDTO) {
        Patient updatedPatient = mappingUtils.mapToPatient(updatedPatientDTO);
        updatedPatient.setPatientId(id);
        patientRepository.save(updatedPatient);
        if (patientRepository.findById(id).equals(updatedPatient)) {
            throw new NotUpdatedException("Patient not updated");
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!patientRepository.findById(id).isPresent()) {
            throw new NotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }
}
