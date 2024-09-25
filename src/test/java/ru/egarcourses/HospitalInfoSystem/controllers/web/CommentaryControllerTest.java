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
import ru.egarcourses.HospitalInfoSystem.services.impl.CommentaryServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;

import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class CommentaryControllerTest {
    @Mock
    CommentaryServiceImpl commentaryServiceImpl;

    @Mock
    DoctorServiceImpl doctorServiceImpl;

    @InjectMocks
    CommentaryController commentaryController;

    private MockMvc mockMvc;

    private final long ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentaryController).build();
    }

    @Test
    public void testGetCommentaries() throws Exception {
        mockMvc.perform(get("/commentaries"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("commentaries"))
                .andExpect(view().name("commentaries/index"));

        verify(commentaryServiceImpl, times(1)).findAll();
    }

    @Test
    public void testGetCommentary() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(ID, 4, "description", doctorDTO);
        doReturn(commentaryDTO).when(commentaryServiceImpl).findById(ID);
        doReturn(doctorDTO).when(doctorServiceImpl).findById(anyLong());

        mockMvc.perform(get("/commentaries/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("commentary"))
                .andExpect(model().attributeExists("doctor"))
                .andExpect(view().name("commentaries/show"));

        verify(commentaryServiceImpl, times(1)).findById(ID);
    }

    @Test
    public void testNewCommentary() throws Exception {
        doReturn(Collections.emptyList()).when(doctorServiceImpl).findAll();

        mockMvc.perform(get("/commentaries/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorsDTO"))
                .andExpect(view().name("commentaries/new"));
    }

    @Test
    public void testCreateCommentary_Success() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(ID, 4, "description", doctorDTO);

        mockMvc.perform(post("/commentaries")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("score", String.valueOf(commentaryDTO.getScore()))
        .param("description", commentaryDTO.getDescription())
        .param("id",   String.valueOf(doctorDTO.getId())))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/commentaries"));
    }

    @Test
    public void testCreateCommentary_ThrowException() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(ID, 6, "description", doctorDTO);
        doReturn(List.of(doctorDTO)).when(doctorServiceImpl).findAll();

        mockMvc.perform(post("/commentaries")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("score", String.valueOf(commentaryDTO.getScore()))
                        .param("description", commentaryDTO.getDescription())
                        .param("id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorsDTO"))
                .andExpect(view().name("commentaries/new"));
    }

    @Test
    public void testEditCommentary() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(ID, 4, "description", doctorDTO);
        doReturn(commentaryDTO).when(commentaryServiceImpl).findById(ID);
        doReturn(doctorDTO).when(doctorServiceImpl).findById(anyLong());

        mockMvc.perform(get("/commentaries/{id}/edit", ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("commentaryDTO"))
                .andExpect(model().attributeExists("commentaryDoctor"))
                .andExpect(view().name("commentaries/edit"));

        verify(commentaryServiceImpl, times(1)).findById(ID);
        verify(doctorServiceImpl, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateCommentary_Success() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(ID, 4, "description", doctorDTO);
        doReturn(doctorDTO).when(doctorServiceImpl).findById(anyLong());

        mockMvc.perform(patch("/commentaries/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("score", String.valueOf(commentaryDTO.getScore()))
                        .param("description", commentaryDTO.getDescription())
                        .param("doctor.id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/commentaries"));
    }

    @Test
    public void testUpdateCommentary_ThrowException() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(ID, "Petr", "101", mock(SpecialtyDTO.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(ID, 6, "description", doctorDTO);
        doReturn(commentaryDTO).when(commentaryServiceImpl).findById(anyLong());

        mockMvc.perform(patch("/commentaries/{id}", ID)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("score", String.valueOf(commentaryDTO.getScore()))
                        .param("description", commentaryDTO.getDescription())
                        .param("doctor.id",   String.valueOf(doctorDTO.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("commentaryDoctor"))
                .andExpect(view().name("commentaries/edit"));
    }

    @Test
    public void testDeleteCommentary() throws Exception {
        mockMvc.perform(delete("/commentaries/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/commentaries"));

        verify(commentaryServiceImpl, times(1)).delete(ID);
    }
}