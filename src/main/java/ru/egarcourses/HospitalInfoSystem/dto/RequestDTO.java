package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.validation.constraints.Future;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс, описывающий DTO записи на приём
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    /**
     * Поле уникального идентификатора DTO записи на приём
     */
    private Long id;

    /**
     * Поле DTO пациента, записанного на приём
     */
    private PatientDTO patient;

    /**
     * Поле DTO доктора, к которому производится запись
     */
    private DoctorDTO doctor;

    /**
     * Поле желаемой даты приёма
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate desiredDate;

    /**
     * Поле утверждённой даты и времени приёма
     */
    private LocalDateTime approvedDate;
}
