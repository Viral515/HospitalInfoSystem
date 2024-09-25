package ru.egarcourses.HospitalInfoSystem.controllers.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {
    @Mock
    PatientServiceImpl patientServiceImpl;

    @InjectMocks
   PatientController patientController;

    private MockMvc mockMvc;

    private final long ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    public void testGetPatients() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patients"))
                .andExpect(view().name("patients/index"));

        verify(patientServiceImpl, times(1)).findAll();
    }

    @Test
    public void testGetPatient() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan", 20, "11111111111", "11111111111");
        doReturn(patientDTO).when(patientServiceImpl).findById(ID);

        mockMvc.perform(get("/patients/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patients/show"));

        verify(patientServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testNewPatient() throws Exception {
        mockMvc.perform(get("/patients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/new"));
    }

    @Test
    public void testCreatePatient_Success() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", patientDTO.getFullName())
                        .param("age", String.valueOf(patientDTO.getAge()))
                        .param("policyNumber",   patientDTO.getPolicyNumber())
                        .param("phoneNumber",   patientDTO.getPhoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

    @Test
    public void testCreatePatient_ThrowException() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan", 20, "11111111111", "11111111111");

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", patientDTO.getFullName())
                        .param("age", String.valueOf(patientDTO.getAge()))
                        .param("policyNumber",   patientDTO.getPolicyNumber())
                        .param("phoneNumber",   patientDTO.getPhoneNumber()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/new"));
    }

    @Test
    public void testEditPatient() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");
        doReturn(patientDTO).when(patientServiceImpl).findById(ID);

        mockMvc.perform(get("/patients/{id}/edit", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patients/edit"));

        verify(patientServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testUpdatePatient_Success() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");

        mockMvc.perform(patch("/patients/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", patientDTO.getFullName())
                        .param("age", String.valueOf(patientDTO.getAge()))
                        .param("policyNumber",   patientDTO.getPolicyNumber())
                        .param("phoneNumber",   patientDTO.getPhoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

    @Test
    public void testUpdatePatient_ThrowException() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan", 20, "11111111111", "11111111111");

        mockMvc.perform(patch("/patients/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", patientDTO.getFullName())
                        .param("age", String.valueOf(patientDTO.getAge()))
                        .param("policyNumber",   patientDTO.getPolicyNumber())
                        .param("phoneNumber",   patientDTO.getPhoneNumber()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/edit"));
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/patients/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));

        verify(patientServiceImpl, times(1)).delete(ID);
    }
}