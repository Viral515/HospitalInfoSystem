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
import ru.egarcourses.HospitalInfoSystem.dto.*;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.RequestServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {
    @Mock
    RequestServiceImpl requestServiceImpl;

    @Mock
    DoctorServiceImpl doctorServiceImpl;

    @Mock
    PatientServiceImpl patientServiceImpl;

    @InjectMocks
    RequestController requestController;

    private MockMvc mockMvc;

    private final long ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
    }

    @Test
    public void testGetRequests() throws Exception {
        mockMvc.perform(get("/requests"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("requests"))
                .andExpect(view().name("requests/index"));

        verify(requestServiceImpl, times(1)).findAll();
    }

    @Test
    public void testGetRequest() throws Exception {
        RequestDTO requestDTO = mock(RequestDTO.class);
        doReturn(requestDTO).when(requestServiceImpl).findById(ID);

        mockMvc.perform(get("/requests/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("request"))
                .andExpect(view().name("requests/show"));

        verify(requestServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testNewRequest() throws Exception {
        doReturn(Collections.emptyList()).when(patientServiceImpl).findAll();
        doReturn(Collections.emptyList()).when(doctorServiceImpl).findAll();

        mockMvc.perform(get("/requests/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(view().name("requests/new"));
    }

    @Test
    public void testCreateRequest_Success() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(ID, patientDTO, doctorDTO, LocalDate.of(2999, 01,01), LocalDateTime.now());

        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("patient.id", String.valueOf(patientDTO.getPatientId()))
                        .param("id", String.valueOf(doctorDTO.getId()))
                        .param("desiredDate",   String.valueOf(requestDTO.getDesiredDate())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/requests"));
    }

    @Test
    public void testCreateRequest_ThrowException() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(ID, patientDTO, doctorDTO, LocalDate.now(), LocalDateTime.now());
        doReturn(List.of(patientDTO)).when(patientServiceImpl).findAll();
        doReturn(List.of(doctorDTO)).when(doctorServiceImpl).findAll();

        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("patient.id", String.valueOf(patientDTO.getPatientId()))
                        .param("id", String.valueOf(doctorDTO.getId()))
                        .param("desiredDate",   String.valueOf(requestDTO.getDesiredDate())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(view().name("requests/new"));
    }

    @Test
    public void testEditRequest() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(ID, patientDTO, doctorDTO, LocalDate.of(2999, 01,01), LocalDateTime.now());
        doReturn(requestDTO).when(requestServiceImpl).findById(ID);

        mockMvc.perform(get("/requests/{id}/edit", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("request"))
                .andExpect(model().attributeExists("requestPatient"))
                .andExpect(model().attributeExists("requestDoctor"))
                .andExpect(view().name("requests/edit"));

        verify(requestServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testUpdateRequest_Success() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(ID, patientDTO, doctorDTO, LocalDate.of(2999, 01,01), LocalDateTime.now());
        doReturn(patientDTO).when(patientServiceImpl).findById(anyLong());
        doReturn(doctorDTO).when(doctorServiceImpl).findById(anyLong());

        mockMvc.perform(patch("/requests/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("patient.patientId", String.valueOf(patientDTO.getPatientId()))
                        .param("doctor.id", String.valueOf(doctorDTO.getId()))
                        .param("desiredDate",   String.valueOf(requestDTO.getDesiredDate())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/requests"));
    }

    @Test
    public void testUpdateRequest_ThrowException() throws Exception {
        PatientDTO patientDTO = new PatientDTO(ID, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(ID, patientDTO, doctorDTO, LocalDate.now(), LocalDateTime.now());
        doReturn(requestDTO).when(requestServiceImpl).findById(anyLong());

        mockMvc.perform(patch("/requests/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("patient.patientId", String.valueOf(patientDTO.getPatientId()))
                        .param("doctor.id", String.valueOf(doctorDTO.getId()))
                        .param("desiredDate",   String.valueOf(requestDTO.getDesiredDate())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("requestPatient"))
                .andExpect(model().attributeExists("requestDoctor"))
                .andExpect(view().name("requests/edit"));
    }

    @Test
    public void testDeleteRequest() throws Exception {
        mockMvc.perform(delete("/requests/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/requests"));

        verify(requestServiceImpl, times(1)).delete(ID);
    }
}