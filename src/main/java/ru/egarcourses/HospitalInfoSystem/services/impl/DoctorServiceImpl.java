package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.repositories.DoctorRepository;
import ru.egarcourses.HospitalInfoSystem.services.DoctorService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

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
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            throw new NotFoundException("Doctors not founded");
        }
        return doctorRepository.findAll().stream().map(mappingUtils::mapToDoctorDTO).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO findById(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (!doctor.isPresent()) {
            throw new NotFoundException("Doctor not found");
        }
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
        if (doctorRepository.findById(updatedDoctor.getId()).equals(updatedDoctor)) {
            throw new NotUpdatedException("Doctor not updated");
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        if (!doctorRepository.findById(id).isPresent()) {
            throw new NotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }
}
