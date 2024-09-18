package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.repositories.PatientRepository;
import ru.egarcourses.HospitalInfoSystem.services.PatientService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;

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
        return patientRepository.findAll().stream().map(mappingUtils::mapToPatientDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(int id) {
        Optional<Patient> foundPatient =  patientRepository.findById(id);
        return mappingUtils.mapToPatientDTO(foundPatient.get());
    }

    @Transactional
    @Override
    public void save(PatientDTO patientDTO) {
        patientRepository.save(mappingUtils.mapToPatient(patientDTO));
    }

    @Transactional
    @Override
    public void update(int id, PatientDTO updatedPatientDTO) {
        Patient updatedPatient = mappingUtils.mapToPatient(updatedPatientDTO);
        updatedPatient.setPatientId(id);
        patientRepository.save(updatedPatient);
    }

    @Transactional
    @Override
    public void delete(int id) {
        patientRepository.deleteById(id);
    }
}
