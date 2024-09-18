package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.repositories.DoctorRepository;
import ru.egarcourses.HospitalInfoSystem.services.DoctorService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, MappingUtils mappingUtils) {
        this.doctorRepository = doctorRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll().stream().map(mappingUtils::mapToDoctorDTO).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO findById(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return mappingUtils.mapToDoctorDTO(doctor.get());
    }

    @Transactional
    @Override
    public void save(DoctorDTO doctorDTO) {
        doctorRepository.save(mappingUtils.mapToDoctor(doctorDTO));
    }

    @Transactional
    @Override
    public void update(int id, DoctorDTO updatedDoctorDTO) {
        Doctor updatedDoctor = mappingUtils.mapToDoctor(updatedDoctorDTO);
        updatedDoctor.setId(id);
        doctorRepository.save(updatedDoctor);
    }

    @Transactional
    @Override
    public void delete(int id) {
        doctorRepository.deleteById(id);
    }
}
