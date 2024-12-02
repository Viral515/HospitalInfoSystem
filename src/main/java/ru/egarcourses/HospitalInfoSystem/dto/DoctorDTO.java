package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;
import lombok.*;

/**
 * Класс, описывающий DTO доктора
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    /**
     * Поле уникального идентификатора DTO доктора
     */
    private Long id;

    /**
     * Поле полного имени и фамилии DTO доктора
     */
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    /**
     * Поле номера кабинета DTO доктора
     */
    @Range(min = 100, max = 999, message = "The score must be a number from 100 to 999")
    @NotNull
    private String cabinet;

    /**
     * Поле DTO специальности DTO доктора
     */
    private SpecialtyDTO specialty;
}
