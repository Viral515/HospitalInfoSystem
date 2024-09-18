package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.repositories.SpecialtyRepository;
import ru.egarcourses.HospitalInfoSystem.services.SpecialtyService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, MappingUtils mappingUtils) {
        this.specialtyRepository = specialtyRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll().stream().map(mappingUtils::mapToSpecialtyDTO).collect(Collectors.toList());
    }

    @Override
    public SpecialtyDTO findById(int id) {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        return mappingUtils.mapToSpecialtyDTO(specialty.get());
    }

    @Transactional
    @Override
    public void save(SpecialtyDTO specialtyDTO) {
        specialtyRepository.save(mappingUtils.mapToSpecialty(specialtyDTO));
    }

    @Transactional
    @Override
    public void update(int id, SpecialtyDTO updatedSpecialtyDTO) {
        Specialty updatedSpecialty = mappingUtils.mapToSpecialty(updatedSpecialtyDTO);
        updatedSpecialty.setId(id);
        specialtyRepository.save(updatedSpecialty);
    }

    @Transactional
    @Override
    public void delete(int id) {
        specialtyRepository.deleteById(id);
    }
}
