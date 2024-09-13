package ru.egarcourses.HospitalInfoSystem.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.models.Patient;
import ru.egarcourses.HospitalInfoSystem.services.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("patients", patientService.findAll());
        return "patients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("patient", patientService.findById(id));
        return "patients/show";
    }

    @GetMapping("/new")
    public String newPatient(@ModelAttribute("patient") Patient patient){
        return "patients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("patient") @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "patients/new";
        }

        patientService.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("patient", patientService.findById(id));
        return "patients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("patient") @Valid Patient patient, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "patients/edit";
        }

        patientService.update(id, patient);
        return "redirect:/patients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        patientService.delete(id);
        return "redirect:/patients";
    }
}
