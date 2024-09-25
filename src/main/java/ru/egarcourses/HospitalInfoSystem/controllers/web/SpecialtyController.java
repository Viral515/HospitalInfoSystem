package ru.egarcourses.HospitalInfoSystem.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.SpecialtyServiceImpl;

/**
 * Класс MVC контроллера для работы с сущностью специальности
 */
@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

    /**
     * Поле реализации сервиса работы со специальностью
     */
    private final SpecialtyServiceImpl specialtyServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param specialtyServiceImpl - сервис для работы со специальностью
     */
    @Autowired
    public SpecialtyController(SpecialtyServiceImpl specialtyServiceImpl) {
        this.specialtyServiceImpl = specialtyServiceImpl;
    }

    /**
     * Функция, возвращающая представление со списком всех специальностей
     *
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление со списком всех специальностей
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("specialties", specialtyServiceImpl.findAll());
        return "specialties/index";
    }

    /**
     * Функция, возвращающая представление с информацией о специальности с конкретным id
     *
     * @param id    - уникальный идентификатор специальности
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление с информацией о специальности с конкретным id
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("specialty", specialtyServiceImpl.findById(id));
        return "specialties/show";
    }

    /**
     * Функция, возвращающая представление с формой создания новой специальности
     *
     * @param specialtyDTO - DTO специальности для получения данных с формы создания
     * @return возвращает представление создания специальности
     */
    @GetMapping("/new")
    public String newSpecialty(@ModelAttribute("specialty") SpecialtyDTO specialtyDTO) {
        return "specialties/new";
    }

    /**
     * Функция, создающая новую запись специальности в базе данных по данным с формы
     *
     * @param specialtyDTO  - DTO специальности для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return возвращает представление списка специальностей при корректных значениях или представление с формой
     * создания, при ошибках валидации формы
     */
    @PostMapping()
    public String create(@ModelAttribute("specialty") @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "specialties/new";
        }

        specialtyServiceImpl.save(specialtyDTO);
        return "redirect:/specialties";
    }

    /**
     * Функция, возвращающая представление с формой обновления полей специальности
     *
     * @param model - модель для передачи параметров на представление
     * @param id    - уникальный идентификатор специальности
     * @return возвращает представление с формой обновления полей специальности
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("specialty", specialtyServiceImpl.findById(id));
        return "specialties/edit";
    }

    /**
     * Функция, обновляющая поля сущности специальности, полученными с формы значениями
     *
     * @param specialtyDTO  - DTO специальности для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор специальности
     * @return возвращает представление списка специальностей при корректных значениях или представление с формой
     * обновления, при ошибках валидации формы
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("specialty") @Valid SpecialtyDTO specialtyDTO, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "specialties/edit";
        }

        specialtyServiceImpl.update(id, specialtyDTO);
        return "redirect:/specialties";
    }

    /**
     * Функция, удаляющая запись специальности с указанным id из базы данных
     *
     * @param id - уникальный идентификатор специальности
     * @return возвращает представление со списком всех специальностей
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        specialtyServiceImpl.delete(id);
        return "redirect:/specialties";
    }
}
