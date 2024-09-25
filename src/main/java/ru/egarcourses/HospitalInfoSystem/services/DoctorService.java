package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;

import java.util.List;

/**
 * Интерфейс сервиса для работы с DTO доктора
 */
public interface DoctorService {

    /**
     * Функция получения списка всех записей из таблицы докторов в базе данных
     *
     * @return возвращает список DTO докторов
     */
    public List<DoctorDTO> findAll();

    /**
     * Функция получения записи из таблицы докторов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденного доктора
     */
    public DoctorDTO findById(Long id);

    /**
     * Функция сохранения новой записи в таблице докторов в базе данных
     *
     * @param doctorDTO - DTO нового доктора
     */
    public void save(DoctorDTO doctorDTO);

    /**
     * Функция обновления существующей записи в таблице докторов в базе данных по заданному id
     *
     * @param id               - уникальный идентификатор записи
     * @param updatedDoctorDTO - DTO обновлённого доктора
     */
    public void update(Long id, DoctorDTO updatedDoctorDTO);

    /**
     * Функция удаления существующей записи в таблице докторов в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    public void delete(Long id);
}
