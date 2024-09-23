package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.repositories.CommentaryRepository;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentaryServiceImplTest {

    @Mock
    private CommentaryRepository commentaryRepository;

    @Mock
    private MappingUtils mappingUtils;

    @InjectMocks
    private CommentaryServiceImpl commentaryServiceImpl;

    @Test
    public void testFindAll_ReturnListOfCommentariesDTO() {
        Commentary commentary1 = new Commentary(1L, 5, "descr1", mock(Doctor.class));
        Commentary commentary2 = new Commentary(2L, 4, "descr2", mock(Doctor.class));
        final List<Commentary> commentaries = new ArrayList<>();
        commentaries.add(commentary1);
        commentaries.add(commentary2);
        CommentaryDTO commentaryDTO1 = mock(CommentaryDTO.class);
        CommentaryDTO commentaryDTO2 = mock(CommentaryDTO.class);
        List<CommentaryDTO> commentariesDTO = new ArrayList<>();
        commentariesDTO.add(commentaryDTO1);
        commentariesDTO.add(commentaryDTO2);
        doReturn(commentaries).when(commentaryRepository).findAll();
        doReturn(commentaryDTO1).when(mappingUtils).mapToCommentaryDTO(commentary1);
        doReturn(commentaryDTO2).when(mappingUtils).mapToCommentaryDTO(commentary2);

        var foundCommentaries = commentaryServiceImpl.findAll();

        foundCommentaries.stream().forEach(s -> assertEquals(CommentaryDTO.class, s.getClass()));
        assertEquals(commentariesDTO, foundCommentaries);
        verify(commentaryRepository, times(1)).findAll();
    }

    @Test
    public void testFindAll_ThrowsNotFoundException() {
        final List<Commentary> commentaries = new ArrayList<>();
        doReturn(commentaries).when(commentaryRepository).findAll();
        assertThrows(NotFoundException.class, () -> commentaryServiceImpl.findAll());
    }

    @Test
    public void testFindById_ReturnCommentaryDTO() {
        Commentary commentary = new Commentary(1L, 5, "descr1", mock(Doctor.class));
        CommentaryDTO commentaryDTO = new CommentaryDTO(1L, 5, "descr1", mock(DoctorDTO.class));
        when(commentaryRepository.findById(1L)).thenReturn(Optional.of(commentary));
        when(mappingUtils.mapToCommentaryDTO(commentary)).thenReturn(commentaryDTO);

        var foundCommentaryDTO = commentaryServiceImpl.findById(1L);

        assertEquals(commentaryDTO, foundCommentaryDTO);
        verify(commentaryRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_ThrowsNotFoundException() {
        when(commentaryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> commentaryServiceImpl.findById(1L));
    }

    @Test
    public void testSave_shouldCallRepository() {
        CommentaryDTO commentaryDTO = mock(CommentaryDTO.class);
        Commentary commentary = mock(Commentary.class);
        doReturn(commentary).when(mappingUtils).mapToCommentary(commentaryDTO);

        commentaryServiceImpl.save(commentaryDTO);

        verify(commentaryRepository, times(1)).save(commentary);
    }

    @Test
    public void testUpdate_Success() {
        CommentaryDTO commentaryDTO = new CommentaryDTO(1L, 5, "descr1", mock(DoctorDTO.class));
        Commentary commentary = new Commentary(1L, 5, "descr1", mock(Doctor.class));
        doReturn(commentary).when(mappingUtils).mapToCommentary(commentaryDTO);
        when(commentaryRepository.findById(1L)).thenReturn(Optional.of(commentary));

        commentaryServiceImpl.update(1L, commentaryDTO);

        verify(commentaryRepository, times(1)).findById(1L);
        verify(commentaryRepository, times(1)).save(commentary);
    }

    @Test
    public void testUpdate_ThrowsNotFoundException() {
        CommentaryDTO commentaryDTO = new CommentaryDTO(1L, 6, "descr1", mock(DoctorDTO.class));

        when(commentaryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentaryServiceImpl.update(1L, commentaryDTO));
    }

    @Test
    public void testDelete_Success() {
        when(commentaryRepository.findById(1L)).thenReturn(Optional.of(mock(Commentary.class)));

        commentaryServiceImpl.delete(1L);
        verify(commentaryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_ThrowsNotFoundException() {
        when(commentaryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentaryServiceImpl.delete(1L));
    }

}