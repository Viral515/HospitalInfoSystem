package ru.egarcourses.HospitalInfoSystem.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;
import ru.egarcourses.HospitalInfoSystem.services.CommentaryService;
import ru.egarcourses.HospitalInfoSystem.services.DoctorService;

@Controller
@RequestMapping("/commentaries")
public class CommentaryController {

    private final CommentaryService commentaryService;
    private final DoctorService doctorService;

    @Autowired
    public CommentaryController(CommentaryService commentaryService, DoctorService doctorService) {
        this.commentaryService = commentaryService;
        this.doctorService = doctorService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("commentaries", commentaryService.findAll());
        return "commentaries/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("commentary", commentaryService.findById(id));
        return "commentaries/show";
    }

    @GetMapping("/new")
    public String newCommentary(@ModelAttribute("commentary") Commentary pcommentary, Model model,
                                @ModelAttribute("doctor") Doctor doctor){
        model.addAttribute("doctors", doctorService.findAll());
        return "commentaries/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("commentary") @Valid Commentary commentary, BindingResult bindingResult,
                         @ModelAttribute("doctor") Doctor doctor, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("doctors", doctorService.findAll());
            return "commentaries/new";
        }
        commentary.setDoctor(doctor);
        commentaryService.save(commentary);
        return "redirect:/commentaries";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("commentary", commentaryService.findById(id));
        return "commentaries/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("commentary") @Valid Commentary commentary, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "commentaries/edit";
        }

        commentaryService.update(id, commentary);
        return "redirect:/commentaries";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        commentaryService.delete(id);
        return "redirect:/commentaries";
    }
}
