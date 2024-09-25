package ru.egarcourses.HospitalInfoSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.egarcourses.HospitalInfoSystem.models.ErrorMessage;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

/**
 * Класс для обработки исключений выброшенных в rest-контроллерах
 */
@RestControllerAdvice
public class ExceptionApiHandler {

    /**
     * Функция обработки исключения NotFoundException
     *
     * @param e - пойманное исключение
     * @return возвращает запрос со статусом NOT_FOUND и сообщением с описанием ошибки
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundedException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }

    /**
     * Функция обработки исключения NotCreatedException
     *
     * @param e - пойманное исключение
     * @return возвращает запрос со статусом UNPROCESSABLE_ENTITY и сообщением с описанием ошибки
     */
    @ExceptionHandler(NotCreatedException.class)
    public ResponseEntity<ErrorMessage> handleNotCreatedException(NotCreatedException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage(e.getMessage()));
    }

    /**
     * Функция обработки исключения NotUpdatedException
     *
     * @param e - пойманное исключение
     * @return возвращает запрос со статусом CONFLICT и сообщением с описанием ошибки
     */
    @ExceptionHandler(NotUpdatedException.class)
    public ResponseEntity<ErrorMessage> handleNotUpdatedException(NotUpdatedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(e.getMessage()));
    }
}
