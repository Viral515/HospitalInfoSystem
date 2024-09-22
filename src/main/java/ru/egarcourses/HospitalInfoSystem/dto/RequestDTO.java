package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RequestDTO {

    private Long id;

    private PatientDTO patient;

    private DoctorDTO doctor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate desiredDate;
}
