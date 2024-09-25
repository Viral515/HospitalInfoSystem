package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * Класс, описывающий сущность доктора
 */
@Entity
@Table(name = "Doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    /**
     * Поле уникального идентификатора доктора
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле полного имени и фамилии доктора
     */
    @Column(name = "full_name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    /**
     * Поле номера кабинета доктора
     */
    @Column(name = "cabinet")
    @Range(min = 100, max = 999, message = "The score must be a number from 100 to 999")
    @NotNull
    @Digits(integer = 3, fraction = 0, message = "The score must be a number")
    private String cabinet;

    /**
     * Поле специальности доктора
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id", referencedColumnName = "id")
    private Specialty specialty;

    /**
     * Поле списка записей на приём у этого доктора
     */
    @OneToMany(mappedBy = "doctor")
    private List<Request> requests;

    /**
     * Поле списка отзывов на этого доктора
     */
    @OneToMany(mappedBy = "doctor")
    private List<Commentary> commentaries;
}
