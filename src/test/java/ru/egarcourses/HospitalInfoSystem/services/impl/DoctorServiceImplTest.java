package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.repositories.DoctorRepository;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private MappingUtils mappingUtils;

    @InjectMocks
    private DoctorServiceImpl doctorServiceImpl;

    @Test
    public void testFindAll_ReturnListOfDoctorsDTO() {
        Doctor doctor1 = mock(Doctor.class);
        Doctor doctor2 = mock(Doctor.class);
        final List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor1);
        doctors.add(doctor2);
        DoctorDTO doctorDTO1 = mock(DoctorDTO.class);
        DoctorDTO doctorDTO2 = mock(DoctorDTO.class);
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        doctorDTOS.add(doctorDTO1);
        doctorDTOS.add(doctorDTO2);
        doReturn(doctors).when(doctorRepository).findAll();
        doReturn(doctorDTO1).when(mappingUtils).mapToDoctorDTO(doctor1);
        doReturn(doctorDTO2).when(mappingUtils).mapToDoctorDTO(doctor2);

        var foundDoctors = doctorServiceImpl.findAll();

        foundDoctors.stream().forEach(s->assertEquals(s.getClass(), DoctorDTO.class));
        assertEquals(doctorDTOS, foundDoctors);
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_ReturnDoctorDTO() {
        Doctor doctor = mock(Doctor.class);
        DoctorDTO doctorDTO = mock(DoctorDTO.class);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(mappingUtils.mapToDoctorDTO(doctor)).thenReturn(doctorDTO);

        var foundDoctorDTO =  doctorServiceImpl.findById(1L);

        assertEquals(doctorDTO, foundDoctorDTO);
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_ThrowsNotFoundException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> doctorServiceImpl.findById(1L));
    }

    @Test
    public void testSave_shouldCallRepository() {
        DoctorDTO doctorDTO = mock(DoctorDTO.class);
        Doctor doctor = mock(Doctor.class);
        doReturn(doctor).when(mappingUtils).mapToDoctor(doctorDTO);

        doctorServiceImpl.save(doctorDTO);

        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void testUpdate_Success() {
        DoctorDTO doctorDTO = mock(DoctorDTO.class);
        Doctor doctor = mock(Doctor.class);
        doReturn(doctor).when(mappingUtils).mapToDoctor(doctorDTO);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        doctorServiceImpl.update(1L, doctorDTO);

        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void testUpdate_ThrowsNotFoundException() {
        DoctorDTO doctorDTO = mock(DoctorDTO.class);

        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> doctorServiceImpl.update(1L, doctorDTO));
    }

    @Test
    public void testDelete_Success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mock(Doctor.class)));

        doctorServiceImpl.delete(1L);
        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_ThrowsNotFoundException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> doctorServiceImpl.delete(1L));
    }

}