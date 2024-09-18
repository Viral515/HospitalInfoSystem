package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;

import java.util.List;

public interface DoctorService {

    public List<DoctorDTO> findAll();

    public DoctorDTO findById(int id);

    public void save(DoctorDTO doctorDTO);

    public void update(int id, DoctorDTO updatedDoctorDTO);

    public void delete(int id);
}
