package ru.egarcourses.HospitalInfoSystem.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public PatientController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("patients", patientServiceImpl.findAll());
        return "patients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("patient", patientServiceImpl.findById(id));
        return "patients/show";
    }

    @GetMapping("/new")
    public String newPatient(@ModelAttribute("patient") PatientDTO patientDTO){
        return "patients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("patient") @Valid PatientDTO patientDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "patients/new";
        }

        patientServiceImpl.save(patientDTO);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("patient", patientServiceImpl.findById(id));
        return "patients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("patient") @Valid PatientDTO patientDTO, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "patients/edit";
        }

        patientServiceImpl.update(id, patientDTO);
        return "redirect:/patients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        patientServiceImpl.delete(id);
        return "redirect:/patients";
    }
}
