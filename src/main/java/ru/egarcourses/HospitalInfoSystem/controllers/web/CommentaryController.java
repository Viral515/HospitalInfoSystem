package ru.egarcourses.HospitalInfoSystem.controllers.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.CommentaryServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.util.List;

@Controller
@RequestMapping("/commentaries")
public class CommentaryController {

    private final CommentaryServiceImpl commentaryServiceImpl;
    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public CommentaryController(CommentaryServiceImpl commentaryServiceImpl, DoctorServiceImpl doctorServiceImpl) {
        this.commentaryServiceImpl = commentaryServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("commentaries", commentaryServiceImpl.findAll());
        return "commentaries/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        CommentaryDTO commentaryDTO = commentaryServiceImpl.findById(id);
        model.addAttribute("commentary", commentaryDTO);
        model.addAttribute("doctor", doctorServiceImpl.findById(commentaryDTO.getDoctor().getId()));
        return "commentaries/show";
    }

    @GetMapping("/new")
    public String newCommentary(@ModelAttribute("commentaryDTO") CommentaryDTO commentaryDTO, Model model,
                                @ModelAttribute("doctorDTO") DoctorDTO doctorDTO) {
        model.addAttribute("doctorsDTO", doctorServiceImpl.findAll());
        return "commentaries/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("commentaryDTO") @Valid CommentaryDTO commentaryDTO, BindingResult bindingResult,
                         @ModelAttribute("doctorDTO") DoctorDTO doctorDTO, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctorsDTO", doctorServiceImpl.findAll());
            return "commentaries/new";
        }
        commentaryDTO.setDoctor(doctorDTO);
        commentaryDTO.setId(0L);
        commentaryServiceImpl.save(commentaryDTO);
        return "redirect:/commentaries";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("commentaryDTO", commentaryServiceImpl.findById(id));
        model.addAttribute("commentaryDoctor", commentaryServiceImpl.findById(id).getDoctor().getFullName());
        return "commentaries/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("commentaryDTO") @Valid CommentaryDTO commentaryDTO, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("commentaryDoctor", commentaryServiceImpl.findById(id).getDoctor().getFullName());
            return "commentaries/edit";
        }

        commentaryDTO.setDoctor(doctorServiceImpl.findById(commentaryDTO.getDoctor().getId()));
        commentaryServiceImpl.update(id, commentaryDTO);
        return "redirect:/commentaries";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        commentaryServiceImpl.delete(id);
        return "redirect:/commentaries";
    }
}
