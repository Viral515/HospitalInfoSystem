package ru.egarcourses.HospitalInfoSystem.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServiceImpl doctorServiceImpl;
    private final SpecialtyServiceImpl specialtyServiceImpl;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorServiceImpl, SpecialtyServiceImpl specialtyServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("doctors", doctorServiceImpl.findAll());
        return "doctors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("doctor", doctorServiceImpl.findById(id));
        return "doctors/show";
    }

    @GetMapping("/new")
    public String newDoctor(@ModelAttribute("doctor") DoctorDTO doctorDTO, Model model,
                            @ModelAttribute("specialty") SpecialtyDTO specialtyDTO) {
        model.addAttribute("specialties", specialtyServiceImpl.findAll());
        return "doctors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("doctor") @Valid DoctorDTO doctorDTO, BindingResult bindingResult,
                         @ModelAttribute("specialty") SpecialtyDTO specialtyDTO, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties", specialtyServiceImpl.findAll());
            return "doctors/new";
        }
        doctorDTO.setSpecialty(specialtyDTO);
        doctorDTO.setId(0L);
        doctorServiceImpl.save(doctorDTO);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("doctor", doctorServiceImpl.findById(id));
        model.addAttribute("doctorSpecialty", doctorServiceImpl.findById(id).getSpecialty().getName());
        return "doctors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("doctor") @Valid DoctorDTO doctorDTO, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctorSpecialty", doctorServiceImpl.findById(id).getSpecialty().getName());
            return "doctors/edit";
        }
        doctorDTO.setSpecialty(specialtyServiceImpl.findById(doctorDTO.getSpecialty().getId()));
        doctorServiceImpl.update(id, doctorDTO);
        return "redirect:/doctors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        doctorServiceImpl.delete(id);
        return "redirect:/doctors";
    }
}
