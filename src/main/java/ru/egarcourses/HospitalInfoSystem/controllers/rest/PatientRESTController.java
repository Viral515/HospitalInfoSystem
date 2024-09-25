package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

/**
 * Класс REST контроллера для работы с сущностью пациента
 */
@RestController
@RequestMapping("/api/patients")
public class PatientRESTController {

    /**
     * Поле реализации сервиса работы с пациентом
     */
    private final PatientServiceImpl patientServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param patientServiceImpl - сервис для работы с пациентом
     */
    @Autowired
    public PatientRESTController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    /**
     * Функция, реализующая get-запрос для получения списка пациентов
     *
     * @return возвращает ответ, содержащий статус запроса и список DTO пациентов, если не возникло ошибок
     */
    @GetMapping()
    public ResponseEntity<List<PatientDTO>> index() {
        final List<PatientDTO> patientDTOList = patientServiceImpl.findAll();
        return ResponseEntity.ok(patientDTOList);
    }

    /**
     * Функция, реализующая get-запрос для получения пациента по заданному id
     *
     * @param id - уникальный идентификатор пациента
     * @return возвращает ответ, содержащий статус запроса и DTO пациента
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> show(@PathVariable("id") Long id) {
        final PatientDTO patientDTO = patientServiceImpl.findById(id);
        return ResponseEntity.ok(patientDTO);
    }

    /**
     * Функция, реализующая post-запрос для создания нового пациента
     *
     * @param patientDTO    - DTO для создания пациента
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return при наличии ошибок пробрасывается исключение NotCreatedException, в случае если запрос
     * содержит корректный DTO пациента, создает пациента и возвращает статус CREATED
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        patientServiceImpl.save(patientDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Функция, реализующая patch-запрос для обновления отзыва по заданному id
     *
     * @param patientDTO    - DTO для обновления пациента
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор пациента
     * @return при наличии ошибок пробрасывается исключение NotUpdatedException, в случае если запрос
     * содержит корректный DTO пациента, обновляет пациента и возвращает статус OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult,
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
        patientServiceImpl.update(id, patientDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Функция, реализующая delete-запрос для удаления пациента по заданному id
     *
     * @param id - уникальный идентификатор пациента
     * @return возвращает статус OK при успешном удалении пациента и статус NOT_FOUND если удаление не удалось.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        patientServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
