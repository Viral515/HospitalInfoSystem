package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * Класс, описывающий сущность пациента
 */
@Entity
@Table(name = "Patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    /**
     * Поле уникального идентификатора пациента
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    /**
     * Поле полного имени и фамилии пациента
     */
    @NotNull(message = "Full name should not be empty.")
    @Size(min = 5, max = 30, message = "Full name should be between 5 and 30 symbols.")
    @Column(name = "full_name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    /**
     * Поле возраста пациента
     */
    @NotNull(message = "Age should be greater than 0.")
    @Column(name = "age")
    @Digits(integer = 3, fraction = 0, message = "The score must be a number")
    @Range(min = 1, max = 123, message = "The score must be a number from 1 to 123")
    private int age;

    /**
     * Поле страхового полиса пациента
     */
    @NotNull(message = "Policy number should not be empty")
    @Size(min = 11, max = 11, message = "The insurance policy number must be 11 characters long.")
    @Digits(integer = 11, fraction = 0, message = "The score must be a number")
    @Column(name = "policy_number")
    private String policyNumber;

    /**
     * Поле номера телефона пациента
     */
    @NotNull(message = "Phone number should not be empty")
    @Size(min = 11, max = 11, message = "The insurance phone number must be 11 characters long.")
    @Column(name = "phone_number")
    @Digits(integer = 11, fraction = 0, message = "The score must be a number")
    private String phoneNumber;

    /**
     * Поле списка записей на приём, оформленные пациентом
     */
    @OneToMany(mappedBy = "patient")
    private List<Request> requests;
}
