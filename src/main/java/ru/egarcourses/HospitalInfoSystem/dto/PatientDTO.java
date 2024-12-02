package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import lombok.*;

/**
 * Класс, описывающий DTO пациента
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    /**
     * Поле уникального идентификатора DTO пациента
     */
    private Long patientId;

    /**
     * Поле полного имени и фамилии DTO пациента
     */
    @NotNull(message = "Full name should not be empty.")
    @Size(min = 5, max = 30, message = "Full name should be between 5 and 30 symbols.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    /**
     * Поле возраста DTO пациента
     */
    @NotNull(message = "Age should be greater than 0.")
    @Range(min = 1, max = 123, message = "The score must be a number from 1 to 123")
    private int age;

    /**
     * Поле страхового номера DTO пациента
     */
    @NotNull(message = "Policy number should not be empty")
    @Size(min = 11, max = 11, message = "The insurance policy number must be 11 characters long.")
    @Digits(integer = 11, fraction = 0, message = "The score must be a number")
    private String policyNumber;

    /**
     * Поле номера телефона DTO пациента
     */
    @NotNull(message = "Phone number should not be empty")
    @Size(min = 11, max = 11, message = "The insurance phone number must be 11 characters long.")
    @Digits(integer = 11, fraction = 0, message = "The score must be a number")
    private String phoneNumber;
}
