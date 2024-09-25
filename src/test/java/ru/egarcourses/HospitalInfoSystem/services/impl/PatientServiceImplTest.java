package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.repositories.PatientRepository;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MappingUtils mappingUtils;

    @InjectMocks
    private PatientServiceImpl patientServiceImpl;

    @Test
    public void testFindAll_ReturnListOfPatientDTO() {
        Patient patient1 = mock(Patient.class);
        Patient patient2 = mock(Patient.class);
        final List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);
        PatientDTO patientDTO1 = mock(PatientDTO.class);
        PatientDTO patientDTO2 = mock(PatientDTO.class);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        patientDTOS.add(patientDTO1);
        patientDTOS.add(patientDTO2);
        doReturn(patients).when(patientRepository).findAll();
        doReturn(patientDTO1).when(mappingUtils).mapToPatientDTO(patient1);
        doReturn(patientDTO2).when(mappingUtils).mapToPatientDTO(patient2);

        var foundPatients = patientServiceImpl.findAll();

        foundPatients.stream().forEach(s->assertEquals(s.getClass(), PatientDTO.class));
        assertEquals(patientDTOS, foundPatients);
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_ReturnPatientDTO() {
        Patient patient = mock(Patient.class);
        PatientDTO patientDTO = mock(PatientDTO.class);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(mappingUtils.mapToPatientDTO(patient)).thenReturn(patientDTO);

        var foundPatientDTO =  patientServiceImpl.findById(1L);

        assertEquals(patientDTO, foundPatientDTO);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_ThrowsNotFoundException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> patientServiceImpl.findById(1L));
    }

    @Test
    public void testSave_shouldCallRepository() {
        PatientDTO patientDTO = mock(PatientDTO.class);
        Patient patient = mock(Patient.class);
        doReturn(patient).when(mappingUtils).mapToPatient(patientDTO);

        patientServiceImpl.save(patientDTO);

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testUpdate_Success() {
        PatientDTO patientDTO = mock(PatientDTO.class);
        Patient patient = mock(Patient.class);
        doReturn(patient).when(mappingUtils).mapToPatient(patientDTO);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientServiceImpl.update(1L, patientDTO);

        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testUpdate_ThrowsNotFoundException() {
        PatientDTO patientDTO = mock(PatientDTO.class);

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> patientServiceImpl.update(1L, patientDTO));
    }

    @Test
    public void testDelete_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mock(Patient.class)));

        patientServiceImpl.delete(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_ThrowsNotFoundException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> patientServiceImpl.delete(1L));
    }
}