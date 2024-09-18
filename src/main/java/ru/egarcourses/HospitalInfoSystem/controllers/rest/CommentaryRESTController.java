package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.CommentaryServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.util.CommentaryNotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.CommentaryNotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/api/commentaries")
public class CommentaryRESTController {

    private final CommentaryServiceImpl commentaryServiceImpl;
    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public CommentaryRESTController(CommentaryServiceImpl commentaryServiceImpl, DoctorServiceImpl doctorServiceImpl) {
        this.commentaryServiceImpl = commentaryServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<CommentaryDTO>> index(){
        final List<CommentaryDTO> commentaries =  commentaryServiceImpl.findAll();
        return !commentaries.isEmpty()
                ? new ResponseEntity<>(commentaries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentaryDTO> show(@PathVariable("id") int id){
        final CommentaryDTO commentaryDTO = commentaryServiceImpl.findById(id);
        return commentaryDTO != null
                ? new ResponseEntity<>(commentaryDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CommentaryDTO commentaryDTO,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new CommentaryNotCreatedException(errorMessage.toString());
        }
        commentaryDTO.setDoctorId(doctorServiceImpl.findById(commentaryDTO.getDoctorId()).getId());
        commentaryServiceImpl.save(commentaryDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid CommentaryDTO commentaryDTO, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new CommentaryNotUpdatedException(errorMessage.toString());
        }
        commentaryServiceImpl.update(id, commentaryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        commentaryServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
