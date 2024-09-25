package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

/**
 * Класс, описывающий сущность отзыва
 */
@Entity
@Table(name = "Commentary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentary {

    /**
     * Поле уникального идентификатора отзыва
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле оценки отзыва
     */
    @Column(name = "score")
    @Range(min = 1, max = 5, message = "The score must be a number from 1 to 5")
    @NotNull
    private int score;

    /**
     * Поле описания отзыва
     */
    @Column(name = "description")
    private String description;

    /**
     * Поле доктора на которого написан отзыв
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
}
