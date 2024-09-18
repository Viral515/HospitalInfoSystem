package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public class DoctorDTO {

    private int id;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    @Range(min = 100, max = 999, message = "The score must be a number from 100 to 999")
    @NotNull
    @Digits(integer = 3, fraction = 0, message = "The score must be a number")
    private String cabinet;

    private int specialtyId;

    public DoctorDTO() {
    }

    public DoctorDTO(String fullName, String cabinet) {
        this.fullName = fullName;
        this.cabinet = cabinet;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public int getId(){
         return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
