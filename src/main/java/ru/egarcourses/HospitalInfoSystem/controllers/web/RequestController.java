package ru.egarcourses.HospitalInfoSystem.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.RequestServiceImpl;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestServiceImpl requestServiceImpl;
    private final PatientServiceImpl patientServiceImpl;
    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public RequestController(RequestServiceImpl requestServiceImpl, PatientServiceImpl patientServiceImpl, DoctorServiceImpl doctorServiceImpl) {
        this.requestServiceImpl = requestServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("requests", requestServiceImpl.findAll());
        return "requests/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("request", requestServiceImpl.findById(id));
        return "requests/show";
    }

    @GetMapping("/new")
    public String newRequests(@ModelAttribute("request") RequestDTO requestDTO, Model model,
                              @ModelAttribute("patient") PatientDTO patientDTO,
                              @ModelAttribute("doctor") DoctorDTO doctorDTO){
        model.addAttribute("patients", patientServiceImpl.findAll());
        model.addAttribute("doctors", doctorServiceImpl.findAll());
        return "requests/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("request") @Valid RequestDTO requestDTO, BindingResult bindingResult,
                         @ModelAttribute("patient") PatientDTO patientDTO,
                         @ModelAttribute("doctor") DoctorDTO doctorDTO, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("patients", patientServiceImpl.findAll());
            model.addAttribute("doctors", doctorServiceImpl.findAll());
            return "requests/new";
        }
        requestDTO.setPatientId(patientDTO.getId());
        requestDTO.setDoctorId(doctorDTO.getId());
        requestServiceImpl.save(requestDTO);
        return "redirect:/requests";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("request", requestServiceImpl.findById(id));
        return "requests/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("request") @Valid RequestDTO requestDTO, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "requests/edit";
        }

        requestServiceImpl.update(id, requestDTO);
        return "redirect:/requests";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        requestServiceImpl.delete(id);
        return "redirect:/requests";
    }
}
