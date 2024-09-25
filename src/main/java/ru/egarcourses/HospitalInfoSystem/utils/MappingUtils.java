package ru.egarcourses.HospitalInfoSystem.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.egarcourses.HospitalInfoSystem.dto.*;
import ru.egarcourses.HospitalInfoSystem.models.*;

/**
 * Класс маппера для маппинга сущностей проекта в Transfer Objects и обратно.
 */
@Service
public class MappingUtils {

    /**
     * Поле маппера
     */
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Функция маппинга комментария в DTO
     *
     * @param commentary - объект комментария
     * @return возвращает DTO комментария
     */
    public CommentaryDTO mapToCommentaryDTO(Commentary commentary) {
        return modelMapper.map(commentary, CommentaryDTO.class);
    }

    /**
     * Функция маппинга DTO комментария в объект модели
     *
     * @param commentaryDTO - DTO комментария
     * @return возвращает комментария
     */
    public Commentary mapToCommentary(CommentaryDTO commentaryDTO) {
        return modelMapper.map(commentaryDTO, Commentary.class);
    }

    /**
     * Фунцкия маппинга доктора в DTO
     *
     * @param doctor - объект доктора
     * @return возвращает DTO доктора
     */
    public DoctorDTO mapToDoctorDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    /**
     * Функция маппинга DTO доктора в объект модели
     *
     * @param doctorDTO - DTO доктора
     * @return возвращает объект доктора
     */
    public Doctor mapToDoctor(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }

    /**
     * Функция маппинга пациента в DTO
     *
     * @param patient - объект пациента
     * @return возвращает DTO пациента
     */
    public PatientDTO mapToPatientDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    /**
     * Функция маппинга DTO пациента в объект модели
     *
     * @param patientDTO - DTO пациента
     * @return возвращает объект пациента
     */
    public Patient mapToPatient(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    /**
     * Функция маппинга записи в DTO
     *
     * @param request - объект записи
     * @return возвращает DTO записи
     */
    public RequestDTO mapToRequestDTO(Request request) {
        return modelMapper.map(request, RequestDTO.class);
    }

    /**
     * Функция маппинга DTO записи в объект модели
     *
     * @param requestDTO - DTO записи
     * @return возвращает объект записи
     */
    public Request mapToRequest(RequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Request.class);
    }

    /**
     * Функция маппинга специальности в DTO
     *
     * @param specialty - объект специальности
     * @return возвращает DTO специальности
     */
    public SpecialtyDTO mapToSpecialtyDTO(Specialty specialty) {
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }

    /**
     * Функция маппинга DTO специанльности в объект модели
     *
     * @param specialtyDTO - DTO специальности
     * @return возвращает объект специальности
     */
    public Specialty mapToSpecialty(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, Specialty.class);
    }
}
