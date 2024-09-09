package ru.egarcourses.HospitalInfoSystem.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.services.DoctorService;
import ru.egarcourses.HospitalInfoSystem.services.SpecialtyService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final SpecialtyService specialtyService;

    @Autowired
    public DoctorController(DoctorService doctorService, SpecialtyService specialtyService) {
        this.doctorService = doctorService;
        this.specialtyService = specialtyService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("doctors", doctorService.findAll());
        return "doctors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("doctor", doctorService.findById(id));
        return "doctors/show";
    }

    @GetMapping("/new")
    public String newDoctor(@ModelAttribute("doctor") Doctor doctor, Model model,
                            @ModelAttribute("specialty") Specialty specialty){
        model.addAttribute("specialties", specialtyService.findAll());
        return "doctors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("doctor") @Valid Doctor doctor, BindingResult bindingResult,
                         @ModelAttribute("specialty") Specialty specialty){
        if(bindingResult.hasErrors()){
            return "doctors/new";
        }
        doctor.setSpecialty(specialty);
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("doctor", doctorService.findById(id));
        return "doctors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("doctor") @Valid Doctor doctor, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "doctors/edit";
        }

        doctorService.update(id, doctor);
        return "redirect:/doctors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        doctorService.delete(id);
        return "redirect:/doctors";
    }
}
