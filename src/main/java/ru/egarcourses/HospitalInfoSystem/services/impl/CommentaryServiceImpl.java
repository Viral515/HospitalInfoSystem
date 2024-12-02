package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.repositories.CommentaryRepository;
import ru.egarcourses.HospitalInfoSystem.services.CommentaryService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализующий интерфейс сервиса сущности комментария
 */
@Service
@Transactional(readOnly = true)
public class CommentaryServiceImpl implements CommentaryService {

    /**
     * Поле репозитория комментария для работы с базой данных
     */
    private final CommentaryRepository commentaryRepository;
    /**
     * Поле маппера сущностей в DTO и обратно
     */
    private final MappingUtils mappingUtils;

    /**
     * Конструктор - создаёт новый объект сервиса работы с комментариями
     *
     * @param commentaryRepository - объект репозитория комментария
     * @param mappingUtils         - объект маппера сущностей
     */
    @Autowired
    public CommentaryServiceImpl(CommentaryRepository commentaryRepository, MappingUtils mappingUtils) {
        this.commentaryRepository = commentaryRepository;
        this.mappingUtils = mappingUtils;
    }

    /**
     * Функция получения списка всех записей из таблицы комментариев в базе данных
     *
     * @return возвращает список DTO комментариев
     */
    @Override
    public List<CommentaryDTO> findAll() {
        List<Commentary> commentaryList = commentaryRepository.findAll();
        return commentaryList.stream().map(mappingUtils::mapToCommentaryDTO).collect(Collectors.toList());
    }

    /**
     * Функция получения записи из таблицы комментариев в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденного комментария
     */
    @Override
    public CommentaryDTO findById(Long id) {
        Optional<Commentary> foundCommentary = commentaryRepository.findById(id);
        if (foundCommentary.isEmpty()) {
            throw new NotFoundException("Commentary not found");
        }
        return mappingUtils.mapToCommentaryDTO(foundCommentary.get());
    }

    /**
     * Функция сохранения новой записи в таблице комментариев в базе данных
     *
     * @param commentaryDTO - DTO нового комментария
     */
    @Transactional
    @Override
    public void save(CommentaryDTO commentaryDTO) {
        commentaryRepository.save(mappingUtils.mapToCommentary(commentaryDTO));
    }

    /**
     * Функция обновления существующей записи в таблице комментариев в базе данных по заданному id
     *
     * @param id                   - уникальный идентификатор записи
     * @param updatedCommentaryDTO - DTO нобновлённого комментария
     */
    @Transactional
    @Override
    public void update(Long id, CommentaryDTO updatedCommentaryDTO) {
        if (commentaryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Comment not found");
        }
        Commentary updatedCommentary = mappingUtils.mapToCommentary(updatedCommentaryDTO);
        updatedCommentary.setId(id);
        commentaryRepository.save(updatedCommentary);
    }

    /**
     * Функция удаления существующей записи в таблице комментариев в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (commentaryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Comment not found");
        }
        commentaryRepository.deleteById(id);
    }
}
