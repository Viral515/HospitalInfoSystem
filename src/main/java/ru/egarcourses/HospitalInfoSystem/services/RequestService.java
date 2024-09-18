package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;

import java.util.List;

public interface RequestService {

    public List<RequestDTO> findAll();

    public RequestDTO findById(int id);

    public void save(RequestDTO requestDTO);

    public void update(int id, RequestDTO updatedRequestDTO);

    public void delete(int id);
}
