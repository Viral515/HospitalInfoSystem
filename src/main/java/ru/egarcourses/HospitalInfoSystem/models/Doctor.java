package ru.egarcourses.HospitalInfoSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "Doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Doctor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
