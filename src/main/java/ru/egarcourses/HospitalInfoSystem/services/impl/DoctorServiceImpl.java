package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.repositories.DoctorRepository;
import ru.egarcourses.HospitalInfoSystem.services.DoctorService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализующий интерфейс сервиса сщности доктора
 */
@Service
@Transactional(readOnly = true)
public class DoctorServiceImpl implements DoctorService {

    /**
     * Поле репозитория доктора для работы с базой данных
     */
    private final DoctorRepository doctorRepository;
    /**
     * Поле маппера сущностей в DTO и обратно
     */
    private final MappingUtils mappingUtils;

    /**
     * Конструктор - создаёт новый объект сервиса работы с докторами
     *
     * @param doctorRepository - объект репозитория доктора
     * @param mappingUtils     - объект маппера сущностей
     */
    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, MappingUtils mappingUtils) {
        this.doctorRepository = doctorRepository;
        this.mappingUtils = mappingUtils;
    }

    /**
     * Функция получения списка всех записей из таблицы докторов в базе данных
     *
     * @return возвращает список DTO докторов
     */
    @Override
    public List<DoctorDTO> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(mappingUtils::mapToDoctorDTO).collect(Collectors.toList());
    }

    /**
     * Функция получения записи из таблицы докторов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденного доктора
     */
    @Override
    public DoctorDTO findById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new NotFoundException("Doctor not found");
        }
        return mappingUtils.mapToDoctorDTO(doctor.get());
    }

    /**
     * Функция сохранения новой записи в таблице докторов в базе данных
     *
     * @param doctorDTO - DTO нового доктора
     */
    @Transactional
    @Override
    public void save(DoctorDTO doctorDTO) {
        doctorRepository.save(mappingUtils.mapToDoctor(doctorDTO));
    }

    /**
     * Функция обновления существующей записи в таблице докторов в базе данных по заданному id
     *
     * @param id               - уникальный идентификатор записи
     * @param updatedDoctorDTO - DTO обновлённого доктора
     */
    @Transactional
    @Override
    public void update(Long id, DoctorDTO updatedDoctorDTO) {
        if (doctorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Doctor not found");
        }
        Doctor updatedDoctor = mappingUtils.mapToDoctor(updatedDoctorDTO);
        updatedDoctor.setId(id);
        doctorRepository.save(updatedDoctor);
    }

    /**
     * Функция удаления существующей записи в таблице докторов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (doctorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }
}
