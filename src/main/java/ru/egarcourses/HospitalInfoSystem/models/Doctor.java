package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "Doctor")
public class Doctor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    @Column(name = "cabinet")
    @Range(min = 100, max = 999, message = "The score must be a number from 100 to 999")
    @NotNull
    @Digits(integer = 3, fraction = 0, message = "The score must be a number")
    private String cabinet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id", referencedColumnName = "id")
    private Specialty specialty;

    @OneToMany(mappedBy = "doctor")
    private List<Request> requests;

    @OneToMany(mappedBy = "doctor")
    private List<Commentary> commentaries;

    public Doctor() {}

    public Doctor(String fullName, String cabinet) {
        this.fullName = fullName;
        this.cabinet = cabinet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public List<Commentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
