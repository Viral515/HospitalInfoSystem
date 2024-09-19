package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.repositories.SpecialtyRepository;
import ru.egarcourses.HospitalInfoSystem.services.SpecialtyService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

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
        List<Specialty> specialties = specialtyRepository.findAll();
        if (specialties.isEmpty()) {
            throw new NotFoundException("Specialties are not found");
        }
        return specialtyRepository.findAll().stream().map(mappingUtils::mapToSpecialtyDTO).collect(Collectors.toList());
    }

    @Override
    public SpecialtyDTO findById(int id) {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (!specialty.isPresent()) {
            throw new NotFoundException("Specialty not found");
        }
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
        if (!specialtyRepository.findById(id).equals(updatedSpecialty)) {
            throw new NotUpdatedException("Specialty not updated");
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        if (!specialtyRepository.findById(id).isPresent()) {
            throw new NotFoundException("Specialty not found");
        }
        specialtyRepository.deleteById(id);
    }
}
