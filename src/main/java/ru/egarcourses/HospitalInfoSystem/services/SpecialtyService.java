package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;

import java.util.List;

/**
 * Интерфейс сервиса для работы с DTO специальности
 */
public interface SpecialtyService {

    /**
     * Функция получения списка всех записей из таблицы специальности в базе данных
     *
     * @return возвращает список DTO специальностей
     */
    public List<SpecialtyDTO> findAll();

    /**
     * Функция получения записи из таблицы специальности в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденной специальности
     */
    public SpecialtyDTO findById(Long id);

    /**
     * Функция сохранения новой записи в таблице специальностей в базе данных
     *
     * @param specialtyDTO - DTO новой специальности
     */
    public void save(SpecialtyDTO specialtyDTO);

    /**
     * Функция обновления существующей записи в таблице специальностей в базе данных по заданному id
     *
     * @param id                  - уникальный идентификатор записи
     * @param updatedSpecialtyDTO - DTO обновлённой специальности
     */
    public void update(Long id, SpecialtyDTO updatedSpecialtyDTO);

    /**
     * Функция удаления существующей записи в таблице специальностей в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    public void delete(Long id);
}
