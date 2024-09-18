package ru.egarcourses.HospitalInfoSystem.controllers.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.RequestServiceImpl;
import ru.egarcourses.HospitalInfoSystem.util.CommentaryNotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.CommentaryNotUpdatedException;
import ru.egarcourses.HospitalInfoSystem.util.RequestNotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.RequestNotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestRESTController {

    private final RequestServiceImpl requestServiceImpl;
    private final PatientServiceImpl patientServiceImpl;
    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public RequestRESTController(RequestServiceImpl requestServiceImpl, PatientServiceImpl patientServiceImpl, DoctorServiceImpl doctorServiceImpl) {
        this.requestServiceImpl = requestServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<RequestDTO>> index(Model model){
        final List<RequestDTO> requests = requestServiceImpl.findAll();
        return !requests.isEmpty()
                ? new ResponseEntity<>(requests, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> show(@PathVariable("id") int id){
        final RequestDTO requestDTO = requestServiceImpl.findById(id);
        return requestDTO != null
                ? new ResponseEntity<>(requestDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid RequestDTO requestDTO,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new RequestNotCreatedException(errorMessage.toString());
        }
        requestDTO.setDoctorId(doctorServiceImpl.findById(requestDTO.getDoctorId()).getId());
        requestDTO.setPatientId(patientServiceImpl.findById(requestDTO.getPatientId()).getId());
        requestServiceImpl.save(requestDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid RequestDTO requestDTO, BindingResult bindingResult,
                                             @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new RequestNotUpdatedException(errorMessage.toString());
        }
        requestServiceImpl.update(id, requestDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        requestServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}