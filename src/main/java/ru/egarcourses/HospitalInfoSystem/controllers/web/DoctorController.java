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

/**
 * Класс MVC контроллера для работы с сущностью доктора
 */
@Controller
@RequestMapping("/doctors")
public class DoctorController {

    /**
     * Поле реализации сервиса работы с доктором
     */
    private final DoctorServiceImpl doctorServiceImpl;
    /**
     * Поле реализации сервиса работы со специальностью
     */
    private final SpecialtyServiceImpl specialtyServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param doctorServiceImpl    - сервис для работы с доктором
     * @param specialtyServiceImpl - сервис для работы со специальностью
     */
    @Autowired
    public DoctorController(DoctorServiceImpl doctorServiceImpl, SpecialtyServiceImpl specialtyServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    /**
     * Функция, возвращающая представление со списком всех докторов
     *
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление со списком всех докторов
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("doctors", doctorServiceImpl.findAll());
        return "doctors/index";
    }

    /**
     * Функция, возвращающая представление с информацией о докторе с конкретным id
     *
     * @param id    - уникальный идентификатор доктора
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление с информацией о докторе с конкретным id
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("doctor", doctorServiceImpl.findById(id));
        return "doctors/show";
    }

    /**
     * Функция, возвращающая представление с формой создания нового доктора
     *
     * @param doctorDTO    - DTO доктора для получения данных с формы создания
     * @param model        - модель для передачи параметров на представление
     * @param specialtyDTO - DTO специальности для получения данных с формы создания
     * @return возвращает представление создания доктора
     */
    @GetMapping("/new")
    public String newDoctor(@ModelAttribute("doctor") DoctorDTO doctorDTO, Model model,
                            @ModelAttribute("specialty") SpecialtyDTO specialtyDTO) {
        model.addAttribute("specialties", specialtyServiceImpl.findAll());
        return "doctors/new";
    }

    /**
     * Функция, создающая новую запись доктора в базе данных по данным с формы
     *
     * @param doctorDTO     - DTO доктора для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param specialtyDTO  - DTO специальности для получения данных с формы
     * @param model         - модель для передачи параметров на представление
     * @return возвращает представление списка докторов при корректных значениях или представление с формой
     * создания, при ошибках валидации формы
     */
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

    /**
     * Функция, возвращающая представление с формой обновления полей доктора
     *
     * @param model - модель для передачи параметров на представление
     * @param id    - уникальный идентификатор доктора
     * @return возвращает представление с формой обновления полей доктора
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        DoctorDTO doctorDTO = doctorServiceImpl.findById(id);
        model.addAttribute("doctor", doctorDTO);
        model.addAttribute("doctorSpecialty", doctorDTO.getSpecialty().getName());
        return "doctors/edit";
    }

    /**
     * Функция, обновляющая поля сущности доктора, полученными с формы значениями
     *
     * @param doctorDTO     - DTO доктора для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор доктора
     * @param model         - модель для передачи параметров на представление
     * @return возвращает представление списка докторов при корректных значениях или представление с формой
     * обновления, при ошибках валидации формы
     */
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

    /**
     * Функция, удаляющая запись доктора с указанным id из базы данных
     *
     * @param id - уникальный идентификатор доктора
     * @return возвращает представление со списком всех докторов
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        doctorServiceImpl.delete(id);
        return "redirect:/doctors";
    }
}
