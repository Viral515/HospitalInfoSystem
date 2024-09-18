package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRESTController {

    private final SpecialtyServiceImpl specialtyServiceImpl;

    @Autowired
    public SpecialtyRESTController(SpecialtyServiceImpl specialtyServiceImpl) {
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<SpecialtyDTO>> index(){
        final List<SpecialtyDTO> specialties = specialtyServiceImpl.findAll();
        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> show(@PathVariable("id") int id){
        final SpecialtyDTO specialtyDTO = specialtyServiceImpl.findById(id);
        return ResponseEntity.ok(specialtyDTO);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        specialtyServiceImpl.save(specialtyDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult,
                                             @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotUpdatedException(errorMessage.toString());
        }
        specialtyServiceImpl.update(id, specialtyDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        specialtyServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
