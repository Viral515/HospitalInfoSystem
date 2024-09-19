package ru.egarcourses.HospitalInfoSystem.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class PatientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Full name should not be empty.")
    @Size(min = 5, max = 30, message = "Full name should be between 5 and 30 symbols.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String fullName;

    @NotNull(message = "Age should be greater than 0.")
    @Digits(integer = 3, fraction = 0, message = "The score must be a number")
    @Range(min = 1, max = 123, message = "The score must be a number from 1 to 123")
    private int age;

    @NotNull(message = "Policy number should not be empty")
    @Size(min=11, max = 11, message = "The insurance policy number must be 11 characters long.")
    @Digits(integer = 11, fraction = 0, message = "The score must be a number")
    private String policyNumber;

    @NotNull(message = "Phone number should not be empty")
    @Size(min=11, max = 11, message = "The insurance phone number must be 11 characters long.")
    @Digits(integer = 11, fraction = 0, message = "The score must be a number")
    private String phoneNumber;

    public PatientDTO() {
    }

    public PatientDTO(String fullName, int age, String policyNumber, String phoneNumber) {
        this.fullName = fullName;
        this.age = age;
        this.policyNumber = policyNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
