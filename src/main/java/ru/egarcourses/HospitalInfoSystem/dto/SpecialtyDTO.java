package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SpecialtyDTO {

    private Long id;

    @NotNull(message = "Specialty name should not be empty.")
    @Size(min = 3, max = 20, message = "Specialty name should be between 3 and 20 symbols.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String name;
}
