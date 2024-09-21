package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {

    public List<DoctorDTO> findAll();

    public DoctorDTO findById(Long id);

    public void save(DoctorDTO doctorDTO);

    public void update(Long id, DoctorDTO updatedDoctorDTO);

    public void delete(Long id);
}
