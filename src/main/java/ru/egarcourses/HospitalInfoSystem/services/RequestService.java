package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;

import java.util.List;

/**
 * Интерфейс сервиса для работы с DTO записи на приём
 */
public interface RequestService {

    /**
     * Функция получения списка всех записей из таблицы записей на приём в базе данных
     *
     * @return возврает список DTO записей на приём
     */
    public List<RequestDTO> findAll();

    /**
     * Функция получения записи из таблицы специальности в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденной записи на приём
     */
    public RequestDTO findById(Long id);

    /**
     * Функция сохранения новой записи в таблице записей на приём в базе данных
     *
     * @param requestDTO - DTO новой записи
     */
    public void save(RequestDTO requestDTO);

    /**
     * Функция обновления существующей записи в таблице специальностей в базе данных по заданному id
     *
     * @param id                - уникальный идентификатор записи
     * @param updatedRequestDTO - DTO обновлённой записи на приём
     */
    public void update(Long id, RequestDTO updatedRequestDTO);

    /**
     * Функция удаления существующей записи в таблице записей на приём по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    public void delete(Long id);
}
