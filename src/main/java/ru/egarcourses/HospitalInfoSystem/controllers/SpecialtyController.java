package ru.egarcourses.HospitalInfoSystem.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.services.SpecialtyService;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("specialties", specialtyService.findAll());
        return "specialties/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("specialty", specialtyService.findById(id));
        return "specialties/show";
    }

    @GetMapping("/new")
    public String newSpecialty(@ModelAttribute("specialty") Specialty specialty){
        return "specialties/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("specialty") @Valid Specialty specialty, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "specialties/new";
        }

        specialtyService.save(specialty);
        return "redirect:/specialties";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("specialty", specialtyService.findById(id));
        return "specialties/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("specialty") @Valid Specialty specialty, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "specialties/edit";
        }

        specialtyService.update(id, specialty);
        return "redirect:/specialties";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        specialtyService.delete(id);
        return "redirect:/specialties";
    }
}
