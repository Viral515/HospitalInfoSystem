package ru.egarcourses.HospitalInfoSystem.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.egarcourses.HospitalInfoSystem.models.ErrorMessage;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ExceptionApiHandlerTest {
    @Mock
    private NotFoundException notFoundException;

    @Mock
    private NotCreatedException notCreatedException;

    @Mock
    private NotUpdatedException notUpdatedException;

    @InjectMocks
    private ExceptionApiHandler exceptionApiHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleNotFoundedException() {
        String expectedMessage = "Resource not found";
        ErrorMessage expectedErrorMessage = new ErrorMessage(expectedMessage);

        when(notFoundException.getMessage()).thenReturn(expectedMessage);

        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleNotFoundedException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedErrorMessage.getMessage(), response.getBody().getMessage());
    }

    @Test
    public void testHandleNotCreatedException() {
        String expectedMessage = "Resource not created";
        ErrorMessage expectedErrorMessage = new ErrorMessage(expectedMessage);

        when(notCreatedException.getMessage()).thenReturn(expectedMessage);

        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleNotCreatedException(notCreatedException);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(expectedErrorMessage.getMessage(), response.getBody().getMessage());
    }

    @Test
    public void testHandleNotUpdatedException() {
        String expectedMessage = "Resource not updated";
        ErrorMessage expectedErrorMessage = new ErrorMessage(expectedMessage);

        when(notUpdatedException.getMessage()).thenReturn(expectedMessage);

        ResponseEntity<ErrorMessage> response = exceptionApiHandler.handleNotUpdatedException(notUpdatedException);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(expectedErrorMessage.getMessage(), response.getBody().getMessage());
    }
}
