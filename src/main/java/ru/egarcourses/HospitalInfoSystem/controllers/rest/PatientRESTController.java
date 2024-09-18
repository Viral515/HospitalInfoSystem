package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.util.CommentaryNotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.CommentaryNotUpdatedException;
import ru.egarcourses.HospitalInfoSystem.util.PatientNotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.PatientNotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientRESTController {

    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public PatientRESTController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<PatientDTO>> index(){
        final List<PatientDTO> patientDTOList = patientServiceImpl.findAll();
        return !patientDTOList.isEmpty()
                ? ResponseEntity.ok(patientDTOList)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> show(@PathVariable("id") int id){
        final PatientDTO patientDTO = patientServiceImpl.findById(id);
        return patientDTO != null
                ? ResponseEntity.ok(patientDTO)
                : ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new PatientNotCreatedException(errorMessage.toString());
        }
        patientServiceImpl.save(patientDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new PatientNotUpdatedException(errorMessage.toString());
        }
        patientServiceImpl.update(id, patientDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        patientServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
