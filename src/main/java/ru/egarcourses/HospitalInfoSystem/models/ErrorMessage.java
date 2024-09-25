package ru.egarcourses.HospitalInfoSystem.models;

import lombok.*;

/**
 * Класс сообщения об ошибке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    /**
     * Поле с информацией об ошибке
     */
    private String message;

}
