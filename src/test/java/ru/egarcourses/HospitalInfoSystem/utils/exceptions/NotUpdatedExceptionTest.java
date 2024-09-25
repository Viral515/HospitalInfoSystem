package ru.egarcourses.HospitalInfoSystem.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotUpdatedExceptionTest {
    @Test
    public void testNotUpdatedException() {
        //given
        String message = "Not created exception";

        //when
        NotUpdatedException exception = assertThrows(NotUpdatedException.class, () -> {
            throw new NotUpdatedException(message);
        });

        //then
        assertEquals(message, exception.getMessage());
    }
}