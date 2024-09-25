package ru.egarcourses.HospitalInfoSystem.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class NotCreatedExceptionTest {

    @Test
    public void testNotCreatedException() {
        String message = "Not created exception";

        NotCreatedException exception = assertThrows(NotCreatedException.class, () -> {
            throw new NotCreatedException(message);
        });

        assertEquals(message, exception.getMessage());
    }
}