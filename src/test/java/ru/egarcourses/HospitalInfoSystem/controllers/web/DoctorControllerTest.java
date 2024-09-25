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
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {
    @Mock
    DoctorServiceImpl doctorServiceImpl;

    @Mock
    SpecialtyServiceImpl specialtyServiceImpl;

    @InjectMocks
    DoctorController doctorController;

    private MockMvc mockMvc;

    private final long ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    public void testGetDoctors() throws Exception {
        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctors"))
                .andExpect(view().name("doctors/index"));

        verify(doctorServiceImpl, times(1)).findAll();
    }

    @Test
    public void testGetDoctor() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        doReturn(doctorDTO).when(doctorServiceImpl).findById(ID);

        mockMvc.perform(get("/doctors/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctor"))
                .andExpect(view().name("doctors/show"));

        verify(doctorServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testNewDoctor() throws Exception {
        doReturn(Collections.emptyList()).when(specialtyServiceImpl).findAll();

        mockMvc.perform(get("/doctors/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialties"))
                .andExpect(view().name("doctors/new"));
    }

    @Test
    public void testCreateDoctor_Success() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", doctorDTO.getFullName())
                        .param("cabinet", doctorDTO.getCabinet())
                        .param("id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/doctors"));
    }

    @Test
    public void testCreateDoctor_ThrowException() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "1011", mock(SpecialtyDTO.class));
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        doReturn(List.of(specialtyDTO)).when(specialtyServiceImpl).findAll();

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", doctorDTO.getFullName())
                        .param("cabinet", doctorDTO.getCabinet())
                        .param("id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialties"))
                .andExpect(view().name("doctors/new"));
    }

    @Test
    public void testEditDoctor() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "1011", specialtyDTO);
        doReturn(doctorDTO).when(doctorServiceImpl).findById(ID);

        mockMvc.perform(get("/doctors/{id}/edit", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctor"))
                .andExpect(model().attributeExists("doctorSpecialty"))
                .andExpect(view().name("doctors/edit"));

        verify(doctorServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testUpdateDoctor_Success() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", specialtyDTO);
        doReturn(specialtyDTO).when(specialtyServiceImpl).findById(anyLong());

        mockMvc.perform(patch("/doctors/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", doctorDTO.getFullName())
                        .param("cabinet", doctorDTO.getCabinet())
                        .param("specialty.id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/doctors"));
    }

    @Test
    public void testUpdateDoctor_ThrowException() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "1011", specialtyDTO);
        doReturn(doctorDTO).when(doctorServiceImpl).findById(anyLong());

        mockMvc.perform(patch("/doctors/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fullName", doctorDTO.getFullName())
                        .param("cabinet", doctorDTO.getCabinet())
                        .param("specialty.id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorSpecialty"))
                .andExpect(view().name("doctors/edit"));
    }

    @Test
    public void testDeleteDoctor() throws Exception {
        mockMvc.perform(delete("/doctors/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/doctors"));

        verify(doctorServiceImpl, times(1)).delete(ID);
    }
}