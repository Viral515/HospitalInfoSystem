package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int patientId;

    private int doctorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate desiredDate;

    public RequestDTO() {
    }

    public RequestDTO(LocalDate desiredDate) {
        this.desiredDate = desiredDate;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public @Future LocalDate getDesiredDate() {
        return desiredDate;
    }

    public void setDesiredDate(@Future LocalDate desiredDate) {
        this.desiredDate = desiredDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
