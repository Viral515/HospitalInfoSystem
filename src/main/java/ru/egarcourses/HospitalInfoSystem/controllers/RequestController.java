package ru.egarcourses.HospitalInfoSystem.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.models.Request;
import ru.egarcourses.HospitalInfoSystem.services.DoctorService;
import ru.egarcourses.HospitalInfoSystem.services.PatientService;
import ru.egarcourses.HospitalInfoSystem.services.RequestService;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public RequestController(RequestService requestService, PatientService patientService, DoctorService doctorService) {
        this.requestService = requestService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("requests", requestService.findAll());
        return "requests/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("request", requestService.findById(id));
        return "requests/show";
    }

    @GetMapping("/new")
    public String newRequests(@ModelAttribute("request") Request request, Model model,
                            @ModelAttribute("patient") Patient patient,
                            @ModelAttribute("doctor") Doctor doctor){
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.findAll());
        return "requests/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("request") @Valid Request request, BindingResult bindingResult,
                         @ModelAttribute("patient") Patient patient,
                         @ModelAttribute("doctor") Doctor doctor, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("doctors", doctorService.findAll());
            return "requests/new";
        }
        request.setPatient(patient);
        request.setDoctor(doctor);
        requestService.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("request", requestService.findById(id));
        return "requests/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("request") @Valid Request request, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "requests/edit";
        }

        requestService.update(id, request);
        return "redirect:/requests";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        requestService.delete(id);
        return "redirect:/requests";
    }
}
