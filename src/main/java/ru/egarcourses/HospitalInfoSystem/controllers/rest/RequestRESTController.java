package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.RequestServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

/**
 * Класс REST контроллера для работы с сущностью записи на приём
 */
@RestController
@RequestMapping("/api/requests")
public class RequestRESTController {

    /**
     * Поле реализации сервиса работы с записью на приём
     */
    private final RequestServiceImpl requestServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param requestServiceImpl - сервис для работы с записью на приём
     */
    @Autowired
    public RequestRESTController(RequestServiceImpl requestServiceImpl) {
        this.requestServiceImpl = requestServiceImpl;
    }

    /**
     * Функция, реализующая get-запрос для получения списка записей на приём
     *
     * @return возвращает ответ, содержащий статус запроса и список DTO записей на приём, если не возникло ошибок
     */
    @GetMapping()
    public ResponseEntity<List<RequestDTO>> index() {
        final List<RequestDTO> requests = requestServiceImpl.findAll();
        return ResponseEntity.ok(requests);
    }

    /**
     * Функция, реализующая get-запрос для получения записи на приём по заданному id
     *
     * @param id - уникальный идентификатор записи на приём
     * @return возвращает ответ, содержащий статус запроса и DTO записи на приём
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> show(@PathVariable("id") Long id) {
        final RequestDTO requestDTO = requestServiceImpl.findById(id);
        return ResponseEntity.ok(requestDTO);
    }

    /**
     * Функция, реализующая post-запрос для создания новой записи на приём
     *
     * @param requestDTO    - DTO для создания записи на приём
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return при наличии ошибок пробрасывается исключение NotCreatedException, в случае если запрос
     * содержит корректный DTO записи на приём, создает запись на приём и возвращает статус CREATED
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid RequestDTO requestDTO,
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
        requestServiceImpl.save(requestDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Функция, реализующая patch-запрос для обновления записи на приём по заданному id
     *
     * @param requestDTO    - DTO для обновления записи на приём
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор записи на приём
     * @return при наличии ошибок пробрасывается исключение NotUpdatedException, в случае если запрос
     * содержит корректный DTO записи на приём, обновляет запись на приём и возвращает статус OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid RequestDTO requestDTO, BindingResult bindingResult,
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
        requestServiceImpl.update(id, requestDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Функция, реализующая delete-запрос для удаления записи на приём по заданному id
     *
     * @param id - уникальный идентификатор записи на приём
     * @return возвращает статус OK при успешном удалении записи на приём и статус NOT_FOUND если удаление не удалось.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        requestServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
