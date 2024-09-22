package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentaryDTO {

    private Long id;

    @Range(min = 1, max = 5, message = "The score must be a number from 1 to 5")
    @NotNull
    private int score;

    private String description;

    private DoctorDTO doctor;
}