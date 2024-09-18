package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;

import java.util.List;

public interface SpecialtyService {

    public List<SpecialtyDTO> findAll();

    public SpecialtyDTO findById(int id);

    public void save(SpecialtyDTO specialtyDTO);

    public void update(int id, SpecialtyDTO updatedSpecialtyDTO);

    public void delete(int id);
}
