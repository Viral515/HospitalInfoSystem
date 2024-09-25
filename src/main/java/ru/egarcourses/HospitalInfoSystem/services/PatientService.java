package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;

import java.util.List;

/**
 * Интерфейс сервиса для работы с DTO пациента
 */
public interface PatientService {

    /**
     * Функция получения списка всех записей из таблицы пациентов в базе данных
     *
     * @return возвращает список DTO пациентов
     */
    public List<PatientDTO> findAll();

    /**
     * Функция получения записи из таблицы пациентов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденного пациента
     */
    public PatientDTO findById(Long id);

    /**
     * Функция сохранения новой записи в таблице пациентов в базе данных
     *
     * @param patientDTO - DTO нового пациента
     */
    public void save(PatientDTO patientDTO);

    /**
     * Функция обновления существующей записи в таблице пациентов в базе данных по уникальному id
     *
     * @param id                - уникальный идентификатор записи
     * @param updatedPatientDTO - DTO обновлённого пациента
     */
    public void update(Long id, PatientDTO updatedPatientDTO);

    /**
     * Функция удаления существующей записи из таблицы пациентов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    public void delete(Long id);
}
