package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

/**
 * Класс REST контроллера для работы с сущностью доктора
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorRESTController {

    /**
     * Поле реализации сервиса работы с доктором
     */
    private final DoctorServiceImpl doctorServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param doctorServiceImpl - сервис для работы с доктором
     */
    @Autowired
    public DoctorRESTController(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
    }

    /**
     * Функция, реализующая get-запрос для получения списка докторов
     *
     * @return возвращает ответ, содержащий статус запроса и список DTO докторов, если не возникло ошибок
     */
    @GetMapping()
    public ResponseEntity<List<DoctorDTO>> index() {
        final List<DoctorDTO> doctors = doctorServiceImpl.findAll();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Функция, реализующая get-запрос для получения доктора по заданному id
     *
     * @param id - уникальный идентификатор доктора
     * @return возвращает ответ, содержащий статус запроса и DTO доктора
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> show(@PathVariable("id") Long id) {
        final DoctorDTO doctorDTO = doctorServiceImpl.findById(id);
        return ResponseEntity.ok(doctorDTO);
    }

    /**
     * Функция, реализующая post-запрос для создания нового доктора
     *
     * @param doctorDTO     - DTO для создания доктора
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return при наличии ошибок пробрасывается исключение NotCreatedException, в случае если запрос
     * содержит корректный DTO доктора, создает доктора и возвращает статус CREATED
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DoctorDTO doctorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        doctorServiceImpl.save(doctorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Функция, реализующая patch-запрос для обновления доктора по заданному id
     *
     * @param doctorDTO     - DTO для обновления доктора
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор доктора
     * @return при наличии ошибок пробрасывается исключение NotUpdatedException, в случае если запрос
     * содержит корректный DTO доктора, обновляет доктора и возвращает статус OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid DoctorDTO doctorDTO, BindingResult bindingResult,
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
        doctorServiceImpl.update(id, doctorDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Функция, реализующая delete-запрос для удаления доктора по заданному id
     *
     * @param id - уникальный идентификатор доктора
     * @return возвращает статус OK при успешном удалении доктора и статус NOT_FOUND если удаление не удалось.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        doctorServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
