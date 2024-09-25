package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * Класс, описывающий сущность специальности
 */
@Entity
@Table(name = "Specialty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Specialty {

    /**
     * Поле уникального идентификатора специальности
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле названия специальности
     */
    @NotNull(message = "Specialty name should not be empty.")
    @Size(min = 3, max = 20, message = "Specialty name should be between 3 and 20 symbols.")
    @Column(name = "name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String name;

    /**
     * Список докторов, обладающих данной специальностью
     */
    @OneToMany(mappedBy = "specialty")
    private List<Doctor> doctors;
}
