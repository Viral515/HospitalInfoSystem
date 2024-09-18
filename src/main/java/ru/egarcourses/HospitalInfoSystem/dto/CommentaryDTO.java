package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class CommentaryDTO {

    private int id;

    @Range(min = 1, max = 5, message = "The score must be a number from 1 to 5")
    @NotNull
    @Digits(integer = 1, fraction = 0, message = "The score must be a number")
    private int score;

    @Column(name = "description")
    private String description;

    private int doctorId;

    public CommentaryDTO() {
    }

    public CommentaryDTO(int score, String description) {
        this.score = score;
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
