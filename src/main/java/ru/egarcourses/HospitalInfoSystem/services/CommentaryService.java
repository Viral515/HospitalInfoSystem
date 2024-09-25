package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;

import java.util.List;

/**
 * Интерфейс сервиса для работы с DTO комментария
 */
public interface CommentaryService {

    /**
     * Функция получения списка всех записей из таблицы комментариев в базе данных
     *
     * @return возвращает список DTO комментариев
     */
    public List<CommentaryDTO> findAll();

    /**
     * Функция получения записи из таблицы комментариев в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденного комментария
     */
    public CommentaryDTO findById(Long id);

    /**
     * Функция сохранения новой записи в таблице комментариев в базе данных
     *
     * @param commentaryDTO - DTO нового комментария
     */
    public void save(CommentaryDTO commentaryDTO);

    /**
     * Функция обновления существующей записи в таблице комментариев в базе данных по заданному id
     *
     * @param id                   - уникальный идентификатор записи
     * @param updatedCommentaryDTO - DTO нобновлённого комментария
     */
    public void update(Long id, CommentaryDTO updatedCommentaryDTO);

    /**
     * Функция удаления существующей записи в таблице комментариев в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    public void delete(Long id);
}
