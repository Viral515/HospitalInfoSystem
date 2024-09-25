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
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.CommentaryServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentaryRESTControllerTest {

    @Mock
    CommentaryServiceImpl commentaryServiceImpl;

    @InjectMocks
    CommentaryRESTController commentaryRESTController;

    @Test
    void testIndex_ReturnResponseCommentariesWithStatusOk() {
        when(commentaryServiceImpl.findAll()).thenReturn(List.of(new CommentaryDTO()));
        ResponseEntity<List<CommentaryDTO>> response = commentaryRESTController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testShow() {
        when(commentaryServiceImpl.findById(1L)).thenReturn(new CommentaryDTO());
        ResponseEntity<CommentaryDTO> response = commentaryRESTController.show(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate_valid() {
        CommentaryDTO commentaryDTO = new CommentaryDTO();
        commentaryRESTController.create(commentaryDTO, mock(BindingResult.class));
        verify(commentaryServiceImpl).save(commentaryDTO);
    }

    @Test
    public void testCreate_invalid() {
        CommentaryDTO commentaryDTO = new CommentaryDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotCreatedException.class,
                () -> commentaryRESTController.create(commentaryDTO, bindingResult));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(commentaryServiceImpl, times(0)).save(commentaryDTO);
    }

    @Test
    public void testUpdate_valid() {
        CommentaryDTO commentaryDTO = new CommentaryDTO();
        commentaryRESTController.update(commentaryDTO, mock(BindingResult.class),1L);
        verify(commentaryServiceImpl).update(1L, commentaryDTO);
    }

    @Test
    public void testUpdate_invalid() {
        CommentaryDTO commentaryDTO = new CommentaryDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        doReturn(true).when(bindingResult).hasErrors();
        FieldError fieldError = mock(FieldError.class);
        doReturn(List.of(fieldError)).when(bindingResult).getFieldErrors();
        doReturn("field1").when(fieldError).getField();
        doReturn("defaultField1Message").when(fieldError).getDefaultMessage();

        Exception exception = assertThrows(NotUpdatedException.class,
                () -> commentaryRESTController.update(commentaryDTO, bindingResult, 1L));

        assertEquals("field1 - defaultField1Message;", exception.getMessage());
        verify(commentaryServiceImpl, times(0)).update(1L, commentaryDTO);
    }

    @Test
    public void testDelete() {
        commentaryRESTController.delete(1L);
        verify(commentaryServiceImpl).delete(1L);
        assertEquals(HttpStatus.OK, ResponseEntity.ok(HttpStatus.OK).getStatusCode());
    }
}