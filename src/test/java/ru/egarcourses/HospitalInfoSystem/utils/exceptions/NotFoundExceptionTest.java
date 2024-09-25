package ru.egarcourses.HospitalInfoSystem.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {
    @Test
    public void testNotFoundException() {
        String message = "Not created exception";

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(message);
        });

        assertEquals(message, exception.getMessage());
    }
}