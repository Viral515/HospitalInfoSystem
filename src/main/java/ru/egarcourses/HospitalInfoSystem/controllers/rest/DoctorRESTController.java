package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorRESTController {

    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public DoctorRESTController(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<DoctorDTO>> index(){
        final List<DoctorDTO> doctors = doctorServiceImpl.findAll();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> show(@PathVariable("id") int id){
        final DoctorDTO doctorDTO = doctorServiceImpl.findById(id);
        return ResponseEntity.ok(doctorDTO);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DoctorDTO doctorDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        doctorServiceImpl.save(doctorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid DoctorDTO doctorDTO, BindingResult bindingResult,
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
        doctorServiceImpl.update(id, doctorDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        doctorServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
