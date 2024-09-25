package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DoctorDTO {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    @Range(min = 100, max = 999, message = "The score must be a number from 100 to 999")
    @NotNull
    private String cabinet;

    private SpecialtyDTO specialty;
}
