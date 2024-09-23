package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.repositories.SpecialtyRepository;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialtyServiceImplTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private MappingUtils mappingUtils;

    @InjectMocks
    private SpecialtyServiceImpl specialtyServiceImpl;

    @Test
    public void testFindAll_ReturnListOfDoctorsDTO() {
        Specialty specialty1 = mock(Specialty.class);
        Specialty specialty2 = mock(Specialty.class);
        final List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);
        SpecialtyDTO specialtyDTO1 = mock(SpecialtyDTO.class);
        SpecialtyDTO specialtyDTO2 = mock(SpecialtyDTO.class);
        List<SpecialtyDTO> specialtyDTOS = new ArrayList<>();
        specialtyDTOS.add(specialtyDTO1);
        specialtyDTOS.add(specialtyDTO2);
        doReturn(specialties).when(specialtyRepository).findAll();
        doReturn(specialtyDTO1).when(mappingUtils).mapToSpecialtyDTO(specialty1);
        doReturn(specialtyDTO2).when(mappingUtils).mapToSpecialtyDTO(specialty2);

        var foundDoctors = specialtyServiceImpl.findAll();

        foundDoctors.stream().forEach(s->assertEquals(s.getClass(), SpecialtyDTO.class));
        assertEquals(specialtyDTOS, foundDoctors);
        verify(specialtyRepository, times(1)).findAll();
    }

    @Test
    public void testFindAll_ThrowsNotFoundException() {
        final List<Specialty> specialties = new ArrayList<>();
        doReturn(specialties).when(specialtyRepository).findAll();
        assertThrows(NotFoundException.class, () -> specialtyServiceImpl.findAll());
    }

    @Test
    public void testFindById_ReturnDoctorDTO() {
        Specialty specialty = mock(Specialty.class);
        SpecialtyDTO specialtyDTO = mock(SpecialtyDTO.class);
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(specialty));
        when(mappingUtils.mapToSpecialtyDTO(specialty)).thenReturn(specialtyDTO);

        var foundSpecialtyDTO =  specialtyServiceImpl.findById(1L);

        assertEquals(specialtyDTO, foundSpecialtyDTO);
        verify(specialtyRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_ThrowsNotFoundException() {
        when(specialtyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> specialtyServiceImpl.findById(1L));
    }

    @Test
    public void testSave_shouldCallRepository() {
        SpecialtyDTO specialtyDTO = mock(SpecialtyDTO.class);
        Specialty specialty = mock(Specialty.class);
        doReturn(specialty).when(mappingUtils).mapToSpecialty(specialtyDTO);

        specialtyServiceImpl.save(specialtyDTO);

        verify(specialtyRepository, times(1)).save(specialty);
    }

    @Test
    public void testUpdate_Success() {
        SpecialtyDTO specialtyDTO = mock(SpecialtyDTO.class);
        Specialty specialty = mock(Specialty.class);
        doReturn(specialty).when(mappingUtils).mapToSpecialty(specialtyDTO);
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(specialty));

        specialtyServiceImpl.update(1L, specialtyDTO);

        verify(specialtyRepository, times(1)).findById(1L);
        verify(specialtyRepository, times(1)).save(specialty);
    }

    @Test
    public void testUpdate_ThrowsNotFoundException() {
        SpecialtyDTO doctorDTO = mock(SpecialtyDTO.class);

        when(specialtyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyServiceImpl.update(1L, doctorDTO));
    }

    @Test
    public void testDelete_Success() {
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(mock(Specialty.class)));

        specialtyServiceImpl.delete(1L);
        verify(specialtyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_ThrowsNotFoundException() {
        when(specialtyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyServiceImpl.delete(1L));
    }
}