package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Specialty")
public class Specialty {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Specialty name should not be empty.")
    @Size(min = 3, max = 20, message = "Specialty name should be between 3 and 20 symbols.")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "specialty")
    private List<Doctor> doctors;

    public Specialty() {}

    public Specialty(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public String toString() {
        return getName();
    }
}
