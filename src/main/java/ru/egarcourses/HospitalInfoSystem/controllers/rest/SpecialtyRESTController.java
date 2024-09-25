package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

/**
 * Класс REST контроллера для работы с сущностью специальности
 */
@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRESTController {

    /**
     * Поле реализации сервиса работы со специальностью
     */
    private final SpecialtyServiceImpl specialtyServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param specialtyServiceImpl - сервис для работы со специальностью
     */
    @Autowired
    public SpecialtyRESTController(SpecialtyServiceImpl specialtyServiceImpl) {
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    /**
     * Функция, реализующая get-запрос для получения списка специальностей
     *
     * @return возвращает ответ, содержащий статус запроса и список DTO специальностей, если не возникло ошибок
     */
    @GetMapping()
    public ResponseEntity<List<SpecialtyDTO>> index() {
        final List<SpecialtyDTO> specialties = specialtyServiceImpl.findAll();
        return ResponseEntity.ok(specialties);
    }

    /**
     * Функция, реализующая get-запрос для получения специальности по заданному id
     *
     * @param id - уникальный идентификатор специальности
     * @return возвращает ответ, содержащий статус запроса и DTO специальности
     */
    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> show(@PathVariable("id") Long id) {
        final SpecialtyDTO specialtyDTO = specialtyServiceImpl.findById(id);
        return ResponseEntity.ok(specialtyDTO);
    }

    /**
     * Функция, реализующая post-запрос для создания новой специальности
     *
     * @param specialtyDTO  - DTO для создания специальности
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return при наличии ошибок пробрасывается исключение NotCreatedException, в случае если запрос
     * содержит корректный DTO специальности, создает специальность и возвращает статус CREATED
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        specialtyServiceImpl.save(specialtyDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Функция, реализующая patch-запрос для обновления специальности по заданному id
     *
     * @param specialtyDTO  - DTO для обновления специальности
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор специальности
     * @return при наличии ошибок пробрасывается исключение NotUpdatedException, в случае если запрос
     * содержит корректный DTO специальности, обновляет специальность и возвращает статус OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult,
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
        specialtyServiceImpl.update(id, specialtyDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Функция, реализующая delete-запрос для удаления специальности по заданному id
     *
     * @param id - уникальный идентификатор специальности
     * @return возвращает статус OK при успешном удалении специальности и статус NOT_FOUND если удаление не удалось.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        specialtyServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
