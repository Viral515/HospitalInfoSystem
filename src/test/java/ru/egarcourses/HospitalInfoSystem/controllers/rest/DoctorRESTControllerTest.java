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
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorRESTControllerTest {

    @Mock
    DoctorServiceImpl doctorServiceImpl;

    @InjectMocks
    DoctorRESTController doctorRESTController;

    @Test
    void testIndex_ReturnResponseDoctorsWithStatusOk() {
        when(doctorServiceImpl.findAll()).thenReturn(List.of(new DoctorDTO()));
        ResponseEntity<List<DoctorDTO>> response = doctorRESTController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testShow() {
        when(doctorServiceImpl.findById(1L)).thenReturn(new DoctorDTO());
        ResponseEntity<DoctorDTO> response = doctorRESTController.show(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate_valid() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorRESTController.create(doctorDTO, mock(BindingResult.class));
        verify(doctorServiceImpl).save(doctorDTO);
    }

    @Test
    public void testCreate_invalid() {
        DoctorDTO doctorDTO = new DoctorDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotCreatedException.class,
                () -> doctorRESTController.create(doctorDTO, bindingResult));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(doctorServiceImpl, times(0)).save(doctorDTO);
    }

    @Test
    public void testUpdate_valid() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorRESTController.update(doctorDTO, mock(BindingResult.class),1L);
        verify(doctorServiceImpl).update(1L, doctorDTO);
    }

    @Test
    public void testUpdate_invalid() {
        DoctorDTO doctorDTO = new DoctorDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotUpdatedException.class,
                () -> doctorRESTController.update(doctorDTO, bindingResult, 1L));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(doctorServiceImpl, times(0)).update(1L, doctorDTO);
    }

    @Test
    public void testDelete() {
        doctorRESTController.delete(1L);
        verify(doctorServiceImpl).delete(1L);
        assertEquals(HttpStatus.OK, ResponseEntity.ok(HttpStatus.OK).getStatusCode());
    }
}