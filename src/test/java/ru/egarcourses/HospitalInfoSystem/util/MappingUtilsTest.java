package ru.egarcourses.HospitalInfoSystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.egarcourses.HospitalInfoSystem.dto.*;
import ru.egarcourses.HospitalInfoSystem.models.*;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MappingUtilsTest {

//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private MappingUtils mappingUtils;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testMapToCommentaryDTO() {
//        Commentary commentary = new Commentary( 3, "Comment");
//        CommentaryDTO expectedDTO = new CommentaryDTO(3,"Comment");
//
//        when(modelMapper.map(commentary, CommentaryDTO.class)).thenReturn(expectedDTO);
//
//        CommentaryDTO actualDTO = mappingUtils.mapToCommentaryDTO(commentary);
//
//        assertEquals(expectedDTO.getId(), actualDTO.getId());
//        assertEquals(expectedDTO.getScore(), actualDTO.getScore());
//        assertEquals(expectedDTO.getDescription(), actualDTO.getDescription());
//        assertEquals(expectedDTO.getDoctorId(), actualDTO.getDoctorId());
//    }
//
//    @Test
//    public void testMapToCommentary() {
//        CommentaryDTO commentaryDTO = new CommentaryDTO(1,"Comment");
//        Commentary expectedCommentary = new Commentary(1, "Comment");
//
//        when(modelMapper.map(commentaryDTO, Commentary.class)).thenReturn(expectedCommentary);
//
//        Commentary actualCommentary = mappingUtils.mapToCommentary(commentaryDTO);
//
//        assertEquals(expectedCommentary.getId(), actualCommentary.getId());
//        assertEquals(expectedCommentary.getScore(), actualCommentary.getScore());
//        assertEquals(expectedCommentary.getDescription(), actualCommentary.getDescription());
//    }
//
//    @Test
//    public void testMapToDoctorDTO() {
//        Doctor doctor = new Doctor( "Dr. Smith", "301");
//        DoctorDTO expectedDTO = new DoctorDTO("Dr. Smith", "301");
//
//        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(expectedDTO);
//
//        DoctorDTO actualDTO = mappingUtils.mapToDoctorDTO(doctor);
//
//        assertEquals(expectedDTO.getId(), actualDTO.getId());
//        assertEquals(expectedDTO.getFullName(), actualDTO.getFullName());
//        assertEquals(expectedDTO.getCabinet(), actualDTO.getCabinet());
//        assertEquals(expectedDTO.getSpecialtyId(), actualDTO.getSpecialtyId());
//    }
//
//    @Test
//    public void testMapToDoctor() {
//        DoctorDTO doctorDTO = new DoctorDTO("Dr. Smith", "301");
//        Doctor expectedDoctor = new Doctor("Dr. Smith", "301");
//
//        when(modelMapper.map(doctorDTO, Doctor.class)).thenReturn(expectedDoctor);
//
//        Doctor actualDoctor = mappingUtils.mapToDoctor(doctorDTO);
//
//        assertEquals(expectedDoctor.getId(), actualDoctor.getId());
//        assertEquals(expectedDoctor.getFullName(), actualDoctor.getFullName());
//        assertEquals(expectedDoctor.getCabinet(), actualDoctor.getCabinet());
//    }
//
//    @Test
//    public void testMapToPatientDTO() {
//        Patient patient = new Patient("John Doe", 20, "111111111111","89131111111");
//        PatientDTO expectedDTO = new PatientDTO("John Doe", 20, "111111111111","89131111111");
//
//        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(expectedDTO);
//
//        PatientDTO actualDTO = mappingUtils.mapToPatientDTO(patient);
//
//        assertEquals(expectedDTO.getId(), actualDTO.getId());
//        assertEquals(expectedDTO.getFullName(), actualDTO.getFullName());
//        assertEquals(expectedDTO.getAge(), actualDTO.getAge());
//        assertEquals(expectedDTO.getPolicyNumber(), actualDTO.getPolicyNumber());
//        assertEquals(expectedDTO.getPhoneNumber(), actualDTO.getPhoneNumber());
//    }
//
//    @Test
//    public void testMapToPatient() {
//        PatientDTO patientDTO = new PatientDTO("John Doe", 20, "111111111111","89131111111");
//        Patient expectedPatient = new Patient("John Doe", 20, "111111111111","89131111111");
//
//        when(modelMapper.map(patientDTO, Patient.class)).thenReturn(expectedPatient);
//
//        Patient actualPatient = mappingUtils.mapToPatient(patientDTO);
//
//        assertEquals(expectedPatient.getPatientId(), actualPatient.getPatientId());
//        assertEquals(expectedPatient.getFullName(), actualPatient.getFullName());
//        assertEquals(expectedPatient.getAge(), actualPatient.getAge());
//        assertEquals(expectedPatient.getPolicyNumber(), actualPatient.getPolicyNumber());
//        assertEquals(expectedPatient.getPhoneNumber(), actualPatient.getPhoneNumber());
//    }
//
//    @Test
//    public void testMapToRequestDTO() {
//        Request request = new Request(LocalDate.parse("2024-10-25"), null);
//        RequestDTO expectedDTO = new RequestDTO(LocalDate.parse("2024-10-25"));
//
//        when(modelMapper.map(request, RequestDTO.class)).thenReturn(expectedDTO);
//
//        RequestDTO actualDTO = mappingUtils.mapToRequestDTO(request);
//
//        assertEquals(expectedDTO.getId(), actualDTO.getId());
//        assertEquals(expectedDTO.getDoctorId(), actualDTO.getDoctorId());
//        assertEquals(expectedDTO.getPatientId(), actualDTO.getPatientId());
//        assertEquals(expectedDTO.getDesiredDate(),actualDTO.getDesiredDate());
//    }
//
//    @Test
//    public void testMapToRequest() {
//        RequestDTO requestDTO = new RequestDTO(LocalDate.parse("2024-10-25"));
//        Request expectedRequest = new Request(LocalDate.parse("2024-10-25"), null);
//
//        when(modelMapper.map(requestDTO, Request.class)).thenReturn(expectedRequest);
//
//        Request actualRequest = mappingUtils.mapToRequest(requestDTO);
//
//        assertEquals(expectedRequest.getId(), actualRequest.getId());
//        assertEquals(expectedRequest.getDesiredDate(), actualRequest.getDesiredDate());
//    }
//
//    @Test
//    public void testMapToSpecialtyDTO() {
//        Specialty specialty = new Specialty("Cardiology");
//        SpecialtyDTO expectedDTO = new SpecialtyDTO("Cardiology");
//
//        when(modelMapper.map(specialty, SpecialtyDTO.class)).thenReturn(expectedDTO);
//
//        SpecialtyDTO actualDTO = mappingUtils.mapToSpecialtyDTO(specialty);
//
//        assertEquals(expectedDTO.getId(), actualDTO.getId());
//        assertEquals(expectedDTO.getName(),actualDTO.getName());
//    }
//
//    @Test
//    public void testMapToSpecialty() {
//        SpecialtyDTO specialtyDTO = new SpecialtyDTO("Cardiology");
//        Specialty expectedSpecialty = new Specialty("Cardiology");
//
//        when(modelMapper.map(specialtyDTO, Specialty.class)).thenReturn(expectedSpecialty);
//
//        Specialty actualSpecialty = mappingUtils.mapToSpecialty(specialtyDTO);
//
//        assertEquals(expectedSpecialty.getId(), actualSpecialty.getId());
//        assertEquals(expectedSpecialty.getName(),actualSpecialty.getName());
//    }
}
