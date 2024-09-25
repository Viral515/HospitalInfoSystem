package ru.egarcourses.HospitalInfoSystem.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.egarcourses.HospitalInfoSystem.dto.*;
import ru.egarcourses.HospitalInfoSystem.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MappingUtilsTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MappingUtils mappingUtils;

    @Test
    void mapToCommentaryDTO() {
        Commentary commentary = new Commentary(1L, 5, "Description", mock(Doctor.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(1L, 5, "Description", mock(DoctorDTO.class));

        CommentaryDTO mappedCommentaryDTO = mappingUtils.mapToCommentaryDTO(commentary);

        assertEquals(commentaryDTO.getId(), mappedCommentaryDTO.getId());
        assertEquals(commentaryDTO.getScore(), mappedCommentaryDTO.getScore());
        assertEquals(commentaryDTO.getDescription(), mappedCommentaryDTO.getDescription());
    }

    @Test
    void mapToCommentary() {
        Commentary commentary = new Commentary(1L, 5, "Description", mock(Doctor.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(1L, 5, "Description", mock(DoctorDTO.class));

        Commentary mappedCommentary = mappingUtils.mapToCommentary(commentaryDTO);

        assertEquals(commentary.getId(), mappedCommentary.getId());
        assertEquals(commentary.getScore(), mappedCommentary.getScore());
        assertEquals(commentary.getDescription(), mappedCommentary.getDescription());
    }

    @Test
    void mapToDoctorDTO() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFullName("John Doe");
        doctor.setCabinet("123");
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        doctor.setSpecialty(specialty);

        DoctorDTO mappedDoctorDTO = mappingUtils.mapToDoctorDTO(doctor);

        assertEquals(1L, mappedDoctorDTO.getId());
        assertEquals("John Doe", mappedDoctorDTO.getFullName());
        assertEquals("123", mappedDoctorDTO.getCabinet());
        assertEquals(1L, mappedDoctorDTO.getSpecialty().getId());
    }

    @Test
    void mapToDoctor() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        doctorDTO.setFullName("John Doe");
        doctorDTO.setCabinet("123");
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setId(1L);
        doctorDTO.setSpecialty(specialtyDTO);

        // when
        Doctor mappedDoctor = mappingUtils.mapToDoctor(doctorDTO);

        // then
        assertEquals(1L, mappedDoctor.getId());
        assertEquals("John Doe", mappedDoctor.getFullName());
        assertEquals("123", mappedDoctor.getCabinet());
        assertEquals(1L, mappedDoctor.getSpecialty().getId());
    }

    @Test
    void mapToPatientDTO() {
        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setFullName("John Doe");
        patient.setAge(30);
        patient.setPolicyNumber("11111111111");
        patient.setPhoneNumber("11111111111");

        PatientDTO mappedPatientDTO = mappingUtils.mapToPatientDTO(patient);

        assertEquals(1L, mappedPatientDTO.getPatientId());
        assertEquals("John Doe", mappedPatientDTO.getFullName());
        assertEquals(30, mappedPatientDTO.getAge());
        assertEquals("11111111111", mappedPatientDTO.getPolicyNumber());
        assertEquals("11111111111", mappedPatientDTO.getPhoneNumber());
    }

    @Test
    void mapToPatient() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(1L);
        patientDTO.setFullName("John Doe");
        patientDTO.setAge(30);
        patientDTO.setPolicyNumber("11111111111");
        patientDTO.setPhoneNumber("11111111111");

        Patient mappedPatient = mappingUtils.mapToPatient(patientDTO);

        assertEquals(1L, mappedPatient.getPatientId());
        assertEquals("John Doe", mappedPatient.getFullName());
        assertEquals(30, mappedPatient.getAge());
        assertEquals("11111111111", mappedPatient.getPolicyNumber());
        assertEquals("11111111111", mappedPatient.getPhoneNumber());
    }

    @Test
    void mapToRequestDTO() {
        Patient patient = new Patient();
        patient.setPatientId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Request request = new Request();
        request.setId(1L);
        request.setPatient(patient);
        request.setDoctor(doctor);
        request.setDesiredDate(LocalDate.now());
        request.setApprovedDate(LocalDateTime.now());

        RequestDTO result = mappingUtils.mapToRequestDTO(request);

        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPatient().getPatientId());
        assertEquals(1L, result.getDoctor().getId());
        assertEquals(LocalDate.now(), result.getDesiredDate());
    }

    @Test
    void mapToRequest() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(1L);
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(1L);
        requestDTO.setPatient(patientDTO);
        requestDTO.setDoctor(doctorDTO);
        requestDTO.setDesiredDate(LocalDate.now());

        // when
        Request result = mappingUtils.mapToRequest(requestDTO);

        // then
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPatient().getPatientId());
        assertEquals(1L, result.getDoctor().getId());
        assertEquals(LocalDate.now(), result.getDesiredDate());
    }

    @Test
    void mapToSpecialtyDTO() {
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Surgeon");

        SpecialtyDTO mappedSpecialty = mappingUtils.mapToSpecialtyDTO(specialty);

        assertEquals(1L, mappedSpecialty.getId());
        assertEquals("Surgeon", mappedSpecialty.getName());
    }

    @Test
    void mapToSpecialty() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setId(1L);
        specialtyDTO.setName("Surgeon");

        Specialty mappedSpecialty = mappingUtils.mapToSpecialty(specialtyDTO);

        assertEquals(1L, mappedSpecialty.getId());
        assertEquals("Surgeon", mappedSpecialty.getName());
    }
}