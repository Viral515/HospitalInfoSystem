package ru.egarcourses.HospitalInfoSystem.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.egarcourses.HospitalInfoSystem.dto.*;
import ru.egarcourses.HospitalInfoSystem.models.*;

@Service
public class MappingUtils {

    private final ModelMapper modelMapper = new ModelMapper();

    public CommentaryDTO mapToCommentaryDTO(Commentary commentary) {
        return modelMapper.map(commentary, CommentaryDTO.class);
    }

    public Commentary mapToCommentary(CommentaryDTO commentaryDTO) {
        return modelMapper.map(commentaryDTO, Commentary.class);
    }

    public DoctorDTO mapToDoctorDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    public Doctor mapToDoctor(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }

    public PatientDTO mapToPatientDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    public Patient mapToPatient(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    public RequestDTO mapToRequestDTO(Request request) {
        return modelMapper.map(request, RequestDTO.class);
    }

    public Request mapToRequest(RequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Request.class);
    }

    public SpecialtyDTO mapToSpecialtyDTO(Specialty specialty) {
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }

    public Specialty mapToSpecialty(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, Specialty.class);
    }
}
