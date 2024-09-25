package ru.egarcourses.HospitalInfoSystem.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;

/**
 * Класс MVC контроллера для работы с сущностью пациента
 */
@Controller
@RequestMapping("/patients")
public class PatientController {

    /**
     * Поле реализации сервиса работы с пациентом
     */
    private final PatientServiceImpl patientServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param patientServiceImpl - сервис для работы с пациентом
     */
    @Autowired
    public PatientController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    /**
     * Функция, возвращающая представление со списком всех пациентов
     *
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление со списком всех пациентов
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("patients", patientServiceImpl.findAll());
        return "patients/index";
    }

    /**
     * Функция, возвращающая представление с информацией о пациенте с конкретным id
     *
     * @param id    - уникальный идентификатор пациента
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление с информацией о пациенте с конкретным id
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("patient", patientServiceImpl.findById(id));
        return "patients/show";
    }

    /**
     * Функция, возвращающая представление с формой создания нового пациента
     *
     * @param patientDTO - DTO пациента для получения данных с формы создания
     * @return возвращает представление создания пациента
     */
    @GetMapping("/new")
    public String newPatient(@ModelAttribute("patient") PatientDTO patientDTO) {
        return "patients/new";
    }

    /**
     * Функция, создающая новую запись пациента в базе данных по данным с формы
     *
     * @param patientDTO    - DTO пациента для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @return возвращает представление списка пациентов при корректных значениях или представление с формой
     * создания, при ошибках валидации формы
     */
    @PostMapping()
    public String create(@ModelAttribute("patient") @Valid PatientDTO patientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "patients/new";
        }

        patientServiceImpl.save(patientDTO);
        return "redirect:/patients";
    }

    /**
     * Функция, возвращающая представление с формой обновления полей пациента
     *
     * @param model - модель для передачи параметров на представление
     * @param id    - уникальный идентификатор пациента
     * @return возвращает представление с формой обновления полей пациента
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("patient", patientServiceImpl.findById(id));
        return "patients/edit";
    }

    /**
     * Функция, обновляющая поля сущности пациента, полученными с формы значениями
     *
     * @param patientDTO    - DTO пациента для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор пациента
     * @return возвращает представление списка пациентов при корректных значениях или представление с формой
     * обновления, при ошибках валидации формы
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("patient") @Valid PatientDTO patientDTO, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "patients/edit";
        }

        patientServiceImpl.update(id, patientDTO);
        return "redirect:/patients";
    }

    /**
     * Функция, удаляющая запись пациента с указанным id из базы данных
     *
     * @param id- уникальный идентификатор пациента
     * @return возвращает представление со списком всех пациентов
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        patientServiceImpl.delete(id);
        return "redirect:/patients";
    }
}
