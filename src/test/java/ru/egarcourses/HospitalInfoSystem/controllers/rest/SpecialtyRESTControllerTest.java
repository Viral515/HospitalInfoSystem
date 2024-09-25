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
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtyRESTControllerTest {
    @Mock
    SpecialtyServiceImpl specialtyServiceImpl;

    @InjectMocks
    SpecialtyRESTController specialtyRESTController;

    @Test
    void testIndex_ReturnResponseSpecialtiesWithStatusOk() {
        when(specialtyServiceImpl.findAll()).thenReturn(List.of(new SpecialtyDTO()));
        ResponseEntity<List<SpecialtyDTO>> response = specialtyRESTController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testShow() {
        when(specialtyServiceImpl.findById(1L)).thenReturn(new SpecialtyDTO());
        ResponseEntity<SpecialtyDTO> response = specialtyRESTController.show(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate_valid() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyRESTController.create(specialtyDTO, mock(BindingResult.class));
        verify(specialtyServiceImpl).save(specialtyDTO);
    }

    @Test
    public void testCreate_invalid() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotCreatedException.class,
                () -> specialtyRESTController.create(specialtyDTO, bindingResult));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(specialtyServiceImpl, times(0)).save(specialtyDTO);
    }

    @Test
    public void testUpdate_valid() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyRESTController.update(specialtyDTO, mock(BindingResult.class),1L);
        verify(specialtyServiceImpl).update(1L, specialtyDTO);
    }

    @Test
    public void testUpdate_invalid() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotUpdatedException.class,
                () -> specialtyRESTController.update(specialtyDTO, bindingResult, 1L));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(specialtyServiceImpl, times(0)).update(1L, specialtyDTO);
    }

    @Test
    public void testDelete() {
        specialtyRESTController.delete(1L);
        verify(specialtyServiceImpl).delete(1L);
        assertEquals(HttpStatus.OK, ResponseEntity.ok(HttpStatus.OK).getStatusCode());
    }
}