package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientRESTControllerTest {
    @Mock
    PatientServiceImpl patientServiceImpl;

    @InjectMocks
    PatientRESTController patientRESTController;

    @Test
    void testIndex_ReturnResponsePatientsWithStatusOk() {
        when(patientServiceImpl.findAll()).thenReturn(List.of(new PatientDTO()));
        ResponseEntity<List<PatientDTO>> response = patientRESTController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testShow() {
        when(patientServiceImpl.findById(1L)).thenReturn(new PatientDTO());
        ResponseEntity<PatientDTO> response = patientRESTController.show(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate_valid() {
        PatientDTO patientDTO = new PatientDTO();
        patientRESTController.create(patientDTO, mock(BindingResult.class));
        verify(patientServiceImpl).save(patientDTO);
    }

    @Test
    public void testCreate_invalid() {
        PatientDTO patientDTO = new PatientDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotCreatedException.class,
                () -> patientRESTController.create(patientDTO, bindingResult));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(patientServiceImpl, times(0)).save(patientDTO);
    }

    @Test
    public void testUpdate_valid() {
        PatientDTO patientDTO = new PatientDTO();
        patientRESTController.update(patientDTO, mock(BindingResult.class),1L);
        verify(patientServiceImpl).update(1L, patientDTO);
    }

    @Test
    public void testUpdate_invalid() {
        PatientDTO patientDTO = new PatientDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotUpdatedException.class,
                () -> patientRESTController.update(patientDTO, bindingResult, 1L));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(patientServiceImpl, times(0)).update(1L, patientDTO);
    }

    @Test
    public void testDelete() {
        patientRESTController.delete(1L);
        verify(patientServiceImpl).delete(1L);
        assertEquals(HttpStatus.OK, ResponseEntity.ok(HttpStatus.OK).getStatusCode());
    }
}