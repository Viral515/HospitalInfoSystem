package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;

import java.util.List;

public interface PatientService {

    public List<PatientDTO> findAll();

    public PatientDTO findById(int id);

    public void save(PatientDTO patient);

    public void update(int id, PatientDTO updatedPatientDTO);

    public void delete(int id);
}
