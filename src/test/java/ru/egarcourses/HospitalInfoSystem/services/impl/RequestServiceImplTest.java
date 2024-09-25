package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.models.*;
import ru.egarcourses.HospitalInfoSystem.repositories.RequestRepository;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private MappingUtils mappingUtils;

    @InjectMocks
    private RequestServiceImpl requestServiceImpl;

    @Test
    public void testFindAll_ReturnListOfDoctorsDTO() {
        Request request1 = mock(Request.class);
        Request request2 = mock(Request.class);
        final List<Request> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);
        RequestDTO requestDTO1 = mock(RequestDTO.class);
        RequestDTO requestDTO2 = mock(RequestDTO.class);
        List<RequestDTO> requestDTOS = new ArrayList<>();
        requestDTOS.add(requestDTO1);
        requestDTOS.add(requestDTO2);
        doReturn(requests).when(requestRepository).findAll();
        doReturn(requestDTO1).when(mappingUtils).mapToRequestDTO(request1);
        doReturn(requestDTO2).when(mappingUtils).mapToRequestDTO(request2);

        var foundRequests = requestServiceImpl.findAll();

        foundRequests.stream().forEach(s -> assertEquals(s.getClass(), RequestDTO.class));
        assertEquals(requestDTOS, foundRequests);
        verify(requestRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_ReturnDoctorDTO() {
        Request request = mock(Request.class);
        RequestDTO requestDTO = mock(RequestDTO.class);
        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(mappingUtils.mapToRequestDTO(request)).thenReturn(requestDTO);

        var foundRequestDTO = requestServiceImpl.findById(1L);

        assertEquals(requestDTO, foundRequestDTO);
        verify(requestRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_ThrowsNotFoundException() {
        when(requestRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> requestServiceImpl.findById(1L));
    }

    @Test
    public void testSave_shouldCallRepository() {
        PatientDTO patientDTO = new PatientDTO(1L, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(1L, patientDTO, doctorDTO, LocalDate.of(2999, 01, 01), LocalDateTime.now());
        Patient patient = new Patient(1L, "Ivan Ivanov", 20, "11111111111", "11111111111", new ArrayList<Request>());
        Doctor doctor = new Doctor(1L, "Ivan Ivanov", "101", mock(Specialty.class), new ArrayList<Request>(), new ArrayList<Commentary>());
        Request request = new Request(1L, patient, doctor, LocalDate.of(2999, 01, 01), LocalDateTime.now());
        doReturn(request).when(mappingUtils).mapToRequest(requestDTO);
        when(requestRepository.findAllByDoctorIdAndDesiredDate(anyLong(),
                any(LocalDate.class))).thenReturn(new ArrayList<Request>());

        requestServiceImpl.save(requestDTO);

        verify(requestRepository, times(1)).save(any(Request.class));
        verify(requestRepository, times(1)).findAllByDoctorIdAndDesiredDate(requestDTO.getDoctor().getId(), requestDTO.getDesiredDate());
    }

    @Test
    public void testSave_GreaterThenMaxCountRequestOnDay() {
        PatientDTO patientDTO = new PatientDTO(1L, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(1L, patientDTO, doctorDTO, LocalDate.of(2999, 01, 01), LocalDateTime.now());
        Patient patient = new Patient(1L, "Ivan Ivanov", 20, "11111111111", "11111111111", new ArrayList<Request>());
        Doctor doctor = new Doctor(1L, "Ivan Ivanov", "101", mock(Specialty.class), new ArrayList<Request>(), new ArrayList<Commentary>());
        Request request = new Request(1L, patient, doctor, LocalDate.of(2999, 01, 01), LocalDateTime.now());
        List<Request> requests = Collections.nCopies(21, new Request());
        doReturn(request).when(mappingUtils).mapToRequest(requestDTO);
        when(requestRepository.findAllByDoctorIdAndDesiredDate(anyLong(),
                any(LocalDate.class))).thenReturn(requests);

        assertThrows(NotCreatedException.class, () -> requestServiceImpl.save(requestDTO));
    }

    @Test
    public void testUpdate_Success() {
        PatientDTO patientDTO = new PatientDTO(1L, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(1L, patientDTO, doctorDTO, LocalDate.of(2999, 01,01), LocalDateTime.now());
        Patient patient = new Patient(1L, "Ivan Ivanov", 20, "11111111111", "11111111111", new ArrayList<Request>());
        Doctor doctor = new Doctor(1L, "Ivan Ivanov", "101", mock(Specialty.class), new ArrayList<Request>(), new ArrayList<Commentary>());
        Request request = new Request(1L, patient, doctor, LocalDate.of(2999, 01,01), LocalDateTime.now());
        doReturn(Optional.of(request)).when(requestRepository).findById(anyLong());
        doReturn(request).when(mappingUtils).mapToRequest(requestDTO);
        when(requestRepository.findAllByDoctorIdAndDesiredDate(anyLong(),
                any(LocalDate.class))).thenReturn(new ArrayList<Request>());

        requestServiceImpl.update(1L, requestDTO);

        verify(requestRepository, times(1)).save(any(Request.class));
        verify(requestRepository, times(1)).findAllByDoctorIdAndDesiredDate(requestDTO.getDoctor().getId(), requestDTO.getDesiredDate());
    }

    @Test
    public void testUpdate_GreaterThenMaxCountRequestOnDay() {
        PatientDTO patientDTO = new PatientDTO(1L, "Ivan Ivanov", 20, "11111111111", "11111111111");
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Ivan Ivanov", "101", mock(SpecialtyDTO.class));
        RequestDTO requestDTO = new RequestDTO(1L, patientDTO, doctorDTO, LocalDate.of(2999, 01,01), LocalDateTime.now());
        Patient patient = new Patient(1L, "Ivan Ivanov", 20, "11111111111", "11111111111", new ArrayList<Request>());
        Doctor doctor = new Doctor(1L, "Ivan Ivanov", "101", mock(Specialty.class), new ArrayList<Request>(), new ArrayList<Commentary>());
        Request request = new Request(1L, patient, doctor, LocalDate.of(2999, 01,01), LocalDateTime.now());
        List<Request> requests = Collections.nCopies(21, new Request());
        doReturn(Optional.of(request)).when(requestRepository).findById(anyLong());
        doReturn(request).when(mappingUtils).mapToRequest(requestDTO);
        when(requestRepository.findAllByDoctorIdAndDesiredDate(anyLong(),
                any(LocalDate.class))).thenReturn(requests);

        assertThrows(NotUpdatedException.class, () -> requestServiceImpl.update(1L, requestDTO));
    }

    @Test
    public void testUpdate_ThrowsNotFoundException() {
        RequestDTO requestDTO = mock(RequestDTO.class);

        when(requestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> requestServiceImpl.update(1L, requestDTO));
    }

    @Test
    public void testDelete_Success() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(mock(Request.class)));

        requestServiceImpl.delete(1L);
        verify(requestRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_ThrowsNotFoundException() {
        when(requestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> requestServiceImpl.delete(1L));
    }
}