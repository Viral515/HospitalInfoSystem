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
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.RequestServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestRESTControllerTest {
    @Mock
    RequestServiceImpl requestServiceImpl;

    @InjectMocks
    RequestRESTController requestRESTController;

    @Test
    void testIndex_ReturnResponseCommentariesWithStatusOk() {
        when(requestServiceImpl.findAll()).thenReturn(List.of(new RequestDTO()));
        ResponseEntity<List<RequestDTO>> response = requestRESTController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testShow() {
        when(requestServiceImpl.findById(1L)).thenReturn(new RequestDTO());
        ResponseEntity<RequestDTO> response = requestRESTController.show(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate_valid() {
        RequestDTO requestDTO = new RequestDTO();
        requestRESTController.create(requestDTO, mock(BindingResult.class));
        verify(requestServiceImpl).save(requestDTO);
    }

    @Test
    public void testCreate_invalid() {
        RequestDTO requestDTO = new RequestDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotCreatedException.class,
                () -> requestRESTController.create(requestDTO, bindingResult));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(requestServiceImpl, times(0)).save(requestDTO);
    }

    @Test
    public void testUpdate_valid() {
        RequestDTO requestDTO = new RequestDTO();
        requestRESTController.update(requestDTO, mock(BindingResult.class),1L);
        verify(requestServiceImpl).update(1L, requestDTO);
    }

    @Test
    public void testUpdate_invalid() {
        RequestDTO requestDTO = new RequestDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotUpdatedException.class,
                () -> requestRESTController.update(requestDTO, bindingResult, 1L));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(requestServiceImpl, times(0)).update(1L, requestDTO);
    }

    @Test
    public void testDelete() {
        requestRESTController.delete(1L);
        verify(requestServiceImpl).delete(1L);
        assertEquals(HttpStatus.OK, ResponseEntity.ok(HttpStatus.OK).getStatusCode());
    }
}