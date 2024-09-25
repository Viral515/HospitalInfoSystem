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
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {
    @Mock
    SpecialtyServiceImpl specialtyServiceImpl;

    @InjectMocks
    SpecialtyController specialtyController;

    private MockMvc mockMvc;

    private final long ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(specialtyController).build();
    }

    @Test
    public void testGetSpecialties() throws Exception {
        mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialties"))
                .andExpect(view().name("specialties/index"));

        verify(specialtyServiceImpl, times(1)).findAll();
    }

    @Test
    public void testGetSpecialty() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        doReturn(specialtyDTO).when(specialtyServiceImpl).findById(ID);

        mockMvc.perform(get("/specialties/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialty"))
                .andExpect(view().name("specialties/show"));

        verify(specialtyServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testNewSpecialty() throws Exception {
        mockMvc.perform(get("/specialties/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("specialties/new"));
    }

    @Test
    public void testCreateSpecialty_Success() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        mockMvc.perform(post("/specialties")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", specialtyDTO.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/specialties"));
    }

    @Test
    public void testCreateSpecialty_ThrowException() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Su");
        mockMvc.perform(post("/specialties")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", specialtyDTO.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("specialties/new"));
    }

    @Test
    public void testEditSpecialty() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Su");
        doReturn(specialtyDTO).when(specialtyServiceImpl).findById(ID);

        mockMvc.perform(get("/specialties/{id}/edit", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialty"))
                .andExpect(view().name("specialties/edit"));

        verify(specialtyServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testUpdateSpecialty_Success() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Surgeon");
        mockMvc.perform(patch("/specialties/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", specialtyDTO.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/specialties"));
    }

    @Test
    public void testUpdateSpecialty_ThrowException() throws Exception {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO(ID, "Su");
        mockMvc.perform(patch("/specialties/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", specialtyDTO.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("specialties/edit"));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        mockMvc.perform(delete("/specialties/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/specialties"));

        verify(specialtyServiceImpl, times(1)).delete(ID);
    }
}