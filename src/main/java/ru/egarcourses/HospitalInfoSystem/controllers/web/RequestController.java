package ru.egarcourses.HospitalInfoSystem.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egarcourses.HospitalInfoSystem.dto.DoctorDTO;
import ru.egarcourses.HospitalInfoSystem.dto.PatientDTO;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.services.impl.DoctorServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.PatientServiceImpl;
import ru.egarcourses.HospitalInfoSystem.services.impl.RequestServiceImpl;

/**
 * Класс MVC контроллера для работы с сущностью записи на приём
 */
@Controller
@RequestMapping("/requests")
public class RequestController {

    /**
     * Поле реализации сервиса работы с записью на приём
     */
    private final RequestServiceImpl requestServiceImpl;
    /**
     * Поле реализации сервиса работы с пациентом
     */
    private final PatientServiceImpl patientServiceImpl;
    /**
     * Поле реализации сервиса работы с доктором
     */
    private final DoctorServiceImpl doctorServiceImpl;

    /**
     * Конструктор - создаёт новый объект класса контроллера
     *
     * @param requestServiceImpl - сервис для работы с записью на приём
     * @param patientServiceImpl - сервис для работы с пациентом
     * @param doctorServiceImpl  - сервис для работы с доктором
     */
    @Autowired
    public RequestController(RequestServiceImpl requestServiceImpl, PatientServiceImpl patientServiceImpl,
                             DoctorServiceImpl doctorServiceImpl) {
        this.requestServiceImpl = requestServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    /**
     * Функция, возвращающая представление со списком всех записей на приём
     *
     * @param model - модель для передачи параметров на представление
     * @return возвращает представление со списком всех записей на приём
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("requests", requestServiceImpl.findAll());
        return "requests/index";
    }

    /**
     * Функция, возвращающая представление с информацией о записи на приём с конкретным id
     *
     * @param id     - уникальный идентификатор записи на приём
     * @param model- модель для передачи параметров на представление
     * @return возвращает представление с информацией о записи на приём с конкретным id
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", requestServiceImpl.findById(id));
        return "requests/show";
    }

    /**
     * Функция, возвращающая представление с формой создания новой записи на приём
     *
     * @param requestDTO - DTO записи на приём для получения данных с формы создания
     * @param model      - модель для передачи параметров на представление
     * @param patientDTO - DTO пациента для получения данных с формы создания
     * @param doctorDTO  - DTO доктора для получения данных с формы создания
     * @return возвращает представление создания записи на приём
     */
    @GetMapping("/new")
    public String newRequests(@ModelAttribute("request") RequestDTO requestDTO, Model model,
                              @ModelAttribute("patient") PatientDTO patientDTO,
                              @ModelAttribute("doctor") DoctorDTO doctorDTO) {
        model.addAttribute("patients", patientServiceImpl.findAll());
        model.addAttribute("doctors", doctorServiceImpl.findAll());
        return "requests/new";
    }

    /**
     * Функция, создающая новую запись записи на приём в базе данных по данным с формы
     *
     * @param requestDTO    - DTO записи на приём для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param patientDTO    - DTO пациента для получения данных с формы
     * @param doctorDTO     - DTO доктора для получения данных с формы
     * @param model         - модель для передачи параметров на представление
     * @return возвращает представление списка записей на приём при корректных значениях или
     * представление с формой создания, при ошибках валидации формы
     */
    @PostMapping()
    public String create(@ModelAttribute("request") @Valid RequestDTO requestDTO, BindingResult bindingResult,
                         @ModelAttribute("patient") PatientDTO patientDTO,
                         @ModelAttribute("doctor") DoctorDTO doctorDTO, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientServiceImpl.findAll());
            model.addAttribute("doctors", doctorServiceImpl.findAll());
            return "requests/new";
        }
        requestDTO.setPatient(patientDTO);
        requestDTO.setDoctor(doctorDTO);
        requestDTO.setId(0L);
        requestServiceImpl.save(requestDTO);
        return "redirect:/requests";
    }

    /**
     * Функция, возвращающая представление с формой обновления полей записи на приём
     *
     * @param model - модель для передачи параметров на представление
     * @param id    - уникальный идентификатор записи на приём
     * @return возвращает представление с формой обновления полей записи на приём
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        RequestDTO requestDTO = requestServiceImpl.findById(id);
        model.addAttribute("request", requestDTO);
        model.addAttribute("requestPatient", requestDTO.getPatient().getFullName());
        model.addAttribute("requestDoctor", requestDTO.getDoctor());
        return "requests/edit";
    }

    /**
     * Функция, обновляющая поля сущности записи на приём, полученными с формы значениями
     *
     * @param requestDTO    - DTO записи на приём для получения данных с формы
     * @param bindingResult - объект, хранящий ошибки валидации формы
     * @param id            - уникальный идентификатор записи на приём
     * @param model         - модель для передачи параметров на представление
     * @return возвращает представление списка записей на приём при корректных значениях или
     * представление с формой обновления, при ошибках валидации формы
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("request") @Valid RequestDTO requestDTO, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("requestPatient", requestServiceImpl.findById(id).getPatient().getFullName());
            model.addAttribute("requestDoctor", requestServiceImpl.findById(id).getDoctor());
            return "requests/edit";
        }

        requestDTO.setPatient(patientServiceImpl.findById(requestDTO.getPatient().getPatientId()));
        requestDTO.setDoctor(doctorServiceImpl.findById(requestDTO.getDoctor().getId()));
        requestServiceImpl.update(id, requestDTO);
        return "redirect:/requests";
    }

    /**
     * Функция, удаляющая запись записи на приём с указанным id из базы данных
     *
     * @param id - уникальный идентификатор записи на приём
     * @return возвращает представление со списком всех записей на приём
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        requestServiceImpl.delete(id);
        return "redirect:/requests";
    }
}
