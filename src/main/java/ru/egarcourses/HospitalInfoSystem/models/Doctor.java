package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Doctor")
public class Doctor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "cabinet")
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
        return "Doctor [id=" + id + ", fullName='" + fullName + '\'' + ", cabinet='" + cabinet + '\'' +  ", specialty="
                + specialty + "]";
    }
}
