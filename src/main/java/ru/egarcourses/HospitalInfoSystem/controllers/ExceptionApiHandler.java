package ru.egarcourses.HospitalInfoSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.egarcourses.HospitalInfoSystem.models.ErrorMessage;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.*;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundedException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotCreatedException.class)
    public ResponseEntity<ErrorMessage> handleNotCreatedException(NotCreatedException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotUpdatedException.class)
    public ResponseEntity<ErrorMessage> handleNotUpdatedException(NotUpdatedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(e.getMessage()));
    }
}
