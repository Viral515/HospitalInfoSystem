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
import ru.egarcourses.HospitalInfoSystem.util.DoctorNotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.DoctorNotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorRESTController {

    private final DoctorServiceImpl doctorServiceImpl;
    private final SpecialtyServiceImpl specialtyServiceImpl;

    @Autowired
    public DoctorRESTController(DoctorServiceImpl doctorServiceImpl, SpecialtyServiceImpl specialtyServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<DoctorDTO>> index(){
        final List<DoctorDTO> doctors = doctorServiceImpl.findAll();
        return !doctors.isEmpty()
                ? ResponseEntity.ok(doctors)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> show(@PathVariable("id") int id){
        final DoctorDTO doctorDTO = doctorServiceImpl.findById(id);
        return doctorDTO != null
                ? ResponseEntity.ok(doctorDTO)
                : ResponseEntity.notFound().build();
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
            throw new DoctorNotCreatedException(errorMessage.toString());
        }
        doctorDTO.setSpecialtyId(specialtyServiceImpl.findById(doctorDTO.getSpecialtyId()).getId());
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
            throw new DoctorNotUpdatedException(errorMessage.toString());
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
