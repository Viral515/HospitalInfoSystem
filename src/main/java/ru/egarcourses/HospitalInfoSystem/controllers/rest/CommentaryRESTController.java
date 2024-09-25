package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.CommentaryServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

/**
 * Класс REST контроллера для работы с сущностью отзыва
 */
@RestController
@RequestMapping("/api/commentaries")
public class CommentaryRESTController {

    /**
     * Поле реализации сервиса работы с отзывом
     */
    private final CommentaryServiceImpl commentaryServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param commentaryServiceImpl - сервис для работы с отзывом
     */
    @Autowired
    public CommentaryRESTController(CommentaryServiceImpl commentaryServiceImpl) {
        this.commentaryServiceImpl = commentaryServiceImpl;
    }

    /**
     * Функция, реализующая get-запрос для получения списка отзывов
     *
     * @return возвращает ответ, содержащий статус запроса и список DTO отзывов, если не возникло ошибок
     */
    @GetMapping()
    public ResponseEntity<List<CommentaryDTO>> index() {
        final List<CommentaryDTO> commentaries = commentaryServiceImpl.findAll();
        return ResponseEntity.ok(commentaries);
    }

    /**
     * Функция, реализующая get-запрос для получения отзыва по заданному id
     *
     * @param id - уникальный идентификатор отзыва
     * @return возвращает ответ, содержащий статус запроса и DTO отзыва
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentaryDTO> show(@PathVariable("id") Long id) {
        final CommentaryDTO commentaryDTO = commentaryServiceImpl.findById(id);
        return ResponseEntity.ok(commentaryDTO);
    }

    /**
     * Функция, реализующая post-запрос для создания нового отзыва
     *
     * @param commentaryDTO - DTO для создания отзыва
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return при наличии ошибок пробрасывается исключение NotCreatedException, в случае если запрос
     * содержит корректный DTO отзыва, создает отзыв и возвращает статус CREATED
     */
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid CommentaryDTO commentaryDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        commentaryServiceImpl.save(commentaryDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Функция, реализующая patch-запрос для обновления отзыва по заданному id
     *
     * @param commentaryDTO - DTO для обновления отзыва
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор отзыва
     * @return при наличии ошибок пробрасывается исключение NotUpdatedException, в случае если запрос
     * содержит корректный DTO отзыва, обновляет отзыв и возвращает статус OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CommentaryDTO commentaryDTO, BindingResult bindingResult,
                                    @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotUpdatedException(errorMessage.toString());
        }
        commentaryServiceImpl.update(id, commentaryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Функция, реализующая delete-запрос для удаления отзыва по заданному id
     *
     * @param id - уникальный идентификатор отзыва
     * @return возвращает статус OK при успешном удалении отзыва и статус NOT_FOUND если удаление не удалось.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        commentaryServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
