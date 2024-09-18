package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SpecialtyDTO {

    private int id;

    @NotNull(message = "Specialty name should not be empty.")
    @Size(min = 3, max = 20, message = "Specialty name should be between 3 and 20 symbols.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String name;

    public SpecialtyDTO() {
    }

    public SpecialtyDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
