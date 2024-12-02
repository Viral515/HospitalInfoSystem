package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.repositories.PatientRepository;
import ru.egarcourses.HospitalInfoSystem.services.PatientService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализующий интерфейс сервиса сущности пациента
 */
@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

    /**
     * Поле репозитория пациента для работы с базой данных
     */
    private final PatientRepository patientRepository;
    /**
     * Поле маппера сущностей в DTO и обратно
     */
    private final MappingUtils mappingUtils;

    /**
     * Коструктор - создаёт новый объект сервиса работы с пациентами
     *
     * @param patientRepository - объект репозитория специальности
     * @param mappingUtils      - объект маппера сущностей
     */
    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, MappingUtils mappingUtils) {
        this.patientRepository = patientRepository;
        this.mappingUtils = mappingUtils;
    }

    /**
     * Функция получения списка всех записей из таблицы пациентов в базе данных
     *
     * @return возвращает список DTO пациентов
     */
    @Override
    public List<PatientDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(mappingUtils::mapToPatientDTO).collect(Collectors.toList());
    }

    /**
     * Функция получения записи из таблицы пациентов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденного пациента
     */
    @Override
    public PatientDTO findById(Long id) {
        Optional<Patient> foundPatient = patientRepository.findById(id);
        if (foundPatient.isEmpty()) {
            throw new NotFoundException("Patient not found");
        }
        return mappingUtils.mapToPatientDTO(foundPatient.get());
    }

    /**
     * Функция сохранения новой записи в таблице пациентов в базе данных
     *
     * @param patientDTO - DTO нового пациента
     */
    @Transactional
    @Override
    public void save(PatientDTO patientDTO) {
        patientRepository.save(mappingUtils.mapToPatient(patientDTO));
    }

    /**
     * Функция обновления существующей записи в таблице пациентов в базе данных по уникальному id
     *
     * @param id                - уникальный идентификатор записи
     * @param updatedPatientDTO - DTO обновлённого пациента
     */
    @Transactional
    @Override
    public void update(Long id, PatientDTO updatedPatientDTO) {
        if (patientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Patient not found");
        }
        Patient updatedPatient = mappingUtils.mapToPatient(updatedPatientDTO);
        updatedPatient.setPatientId(id);
        patientRepository.save(updatedPatient);
    }

    /**
     * Функция удаления существующей записи из таблицы пациентов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (patientRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }
}
