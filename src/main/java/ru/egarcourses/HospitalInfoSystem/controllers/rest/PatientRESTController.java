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
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

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
    public ResponseEntity<List<PatientDTO>> index() {
        final List<PatientDTO> patientDTOList = patientServiceImpl.findAll();
        return ResponseEntity.ok(patientDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> show(@PathVariable("id") Long id) {
        final PatientDTO patientDTO = patientServiceImpl.findById(id);
        return ResponseEntity.ok(patientDTO);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        patientServiceImpl.save(patientDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult,
                                             @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotUpdatedException(errorMessage.toString());
        }
        patientServiceImpl.update(id, patientDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        patientServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
