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

/**
 * Класс MVC контроллера для работы с сущностью отзыва
 */
@Controller
@RequestMapping("/commentaries")
public class CommentaryController {

    /**
     * Поле реализации сервиса работы с отзывом
     */
    private final CommentaryServiceImpl commentaryServiceImpl;
    /**
     * Поле реализации сервиса работы с доктором
     */
    private final DoctorServiceImpl doctorServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param commentaryServiceImpl - сервис для работы с отзывом
     * @param doctorServiceImpl     - сервис для работы с доктором
     */
    @Autowired
    public CommentaryController(CommentaryServiceImpl commentaryServiceImpl, DoctorServiceImpl doctorServiceImpl) {
        this.commentaryServiceImpl = commentaryServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    /**
     * Функция, возвращающая представление со списком всех отзывов
     *
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление со списком всех отзывов
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("commentaries", commentaryServiceImpl.findAll());
        return "commentaries/index";
    }

    /**
     * Функция, возвращающая представление с информацией об отзыве с конкретным id
     *
     * @param id    - уникальный идентификатор отзыва
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление с информацией об отзыве с конкретным id
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        CommentaryDTO commentaryDTO = commentaryServiceImpl.findById(id);
        model.addAttribute("commentary", commentaryDTO);
        model.addAttribute("doctor", doctorServiceImpl.findById(commentaryDTO.getDoctor().getId()));
        return "commentaries/show";
    }

    /**
     * Функция, возвращающая представление с формой создания нового отзыва
     *
     * @param commentaryDTO - DTO отзыва для получения данных с формы создания
     * @param model         - модель для передачи параметров на представление
     * @param doctorDTO     - DTO доктора для получения данных с формы создания
     * @return возвращает представление создания отзыва
     */
    @GetMapping("/new")
    public String newCommentary(@ModelAttribute("commentaryDTO") CommentaryDTO commentaryDTO, Model model,
                                @ModelAttribute("doctorDTO") DoctorDTO doctorDTO) {
        model.addAttribute("doctorsDTO", doctorServiceImpl.findAll());
        return "commentaries/new";
    }

    /**
     * Функция, создающая новую запись отзыва в базе данных по данным с формы
     *
     * @param commentaryDTO - DTO отзыва для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param doctorDTO     - DTO доктора для получения данных с формы
     * @param model         - модель для передачи параметров на представление
     * @return возвращает представление списка отзывов при корректных значениях или представление с формой
     * создания, при ошибках валидации формы
     */
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

    /**
     * Функция, возвращающая представление с формой обновления полей отзыва
     *
     * @param model - модель для передачи параметров на представление
     * @param id    - уникальный идентификатор отзыва
     * @return возвращает представление с формой обновления полей отзыва
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        CommentaryDTO commentaryDTO = commentaryServiceImpl.findById(id);
        model.addAttribute("commentaryDTO", commentaryDTO);
        model.addAttribute("commentaryDoctor",
                doctorServiceImpl.findById(commentaryDTO.getDoctor().getId()).getFullName());
        return "commentaries/edit";
    }

    /**
     * Функция, обновляющая поля сущности отзыва, полученными с формы значениями
     *
     * @param commentaryDTO - DTO отзыва для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор отзыва
     * @param model         - модель для передачи параметров на представление
     * @return возвращает представление списка отзывов при корректных значениях или представление с формой
     * обновления, при ошибках валидации формы
     */
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

    /**
     * Функция, удаляющая запись отзыва с указанным id из базы данных
     *
     * @param id - уникальный идентификатор отзыва
     * @return возвращает представление со списком всех отзывов
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        commentaryServiceImpl.delete(id);
        return "redirect:/commentaries";
    }
}
