package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

/**
 * Класс, описывающий DTO отзыва
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryDTO {

    /**
     * Поле уникального идентификатора DTO отзыва
     */
    private Long id;

    /**
     * Поле оценки DTO отзыва
     */
    @Range(min = 1, max = 5, message = "The score must be a number from 1 to 5")
    @NotNull
    private int score;

    /**
     * Поле описания DTO отзыва
     */
    private String description;

    /**
     * Поле DTO доктора, на которого написан отзыв
     */
    private DoctorDTO doctor;
}