package ru.egarcourses.HospitalInfoSystem.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

    private final SpecialtyServiceImpl specialtyServiceImpl;

    @Autowired
    public SpecialtyController(SpecialtyServiceImpl specialtyServiceImpl) {
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("specialties", specialtyServiceImpl.findAll());
        return "specialties/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("specialty", specialtyServiceImpl.findById(id));
        return "specialties/show";
    }

    @GetMapping("/new")
    public String newSpecialty(@ModelAttribute("specialty") SpecialtyDTO specialtyDTO){
        return "specialties/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("specialty") @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "specialties/new";
        }

        specialtyServiceImpl.save(specialtyDTO);
        return "redirect:/specialties";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("specialty", specialtyServiceImpl.findById(id));
        return "specialties/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("specialty") @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "specialties/edit";
        }

        specialtyServiceImpl.update(id, specialtyDTO);
        return "redirect:/specialties";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        specialtyServiceImpl.delete(id);
        return "redirect:/specialties";
    }
}
