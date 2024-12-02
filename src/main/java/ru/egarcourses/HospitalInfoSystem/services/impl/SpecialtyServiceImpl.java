package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.SpecialtyDTO;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;
import ru.egarcourses.HospitalInfoSystem.repositories.SpecialtyRepository;
import ru.egarcourses.HospitalInfoSystem.services.SpecialtyService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализующий интерфейс сервиса сущности специальности
 */
@Service
@Transactional(readOnly = true)
public class SpecialtyServiceImpl implements SpecialtyService {

    /**
     * Поле репозитория специальности для работы с базой данных
     */
    private final SpecialtyRepository specialtyRepository;
    /**
     * Поле маппера сущностей в DTO и обратно
     */
    private final MappingUtils mappingUtils;

    /**
     * Конструктор - создаёт новый объект сервиса работы со специальностями
     *
     * @param specialtyRepository - объект репозитория специальности
     * @param mappingUtils        - объект маппера сущностей
     */
    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, MappingUtils mappingUtils) {
        this.specialtyRepository = specialtyRepository;
        this.mappingUtils = mappingUtils;
    }

    /**
     * Функция получения списка всех записей из таблицы специальности в базе данных
     *
     * @return возвращает список DTO специальностей
     */
    @Override
    public List<SpecialtyDTO> findAll() {
        List<Specialty> specialties = specialtyRepository.findAll();
        return specialties.stream().map(mappingUtils::mapToSpecialtyDTO).collect(Collectors.toList());
    }

    /**
     * Функция получения записи из таблицы специальности в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO специальности
     */
    @Override
    public SpecialtyDTO findById(Long id) {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (specialty.isEmpty()) {
            throw new NotFoundException("Specialty not found");
        }
        return mappingUtils.mapToSpecialtyDTO(specialty.get());
    }

    /**
     * Функция сохранения новой записи в таблице специальностей в базе данных
     *
     * @param specialtyDTO - DTO новой специальности
     */
    @Transactional
    @Override
    public void save(SpecialtyDTO specialtyDTO) {
        specialtyRepository.save(mappingUtils.mapToSpecialty(specialtyDTO));
    }

    /**
     * Функция обновления существующей записи в таблице специальностей в базе данных по заданному id
     *
     * @param id                  - уникальный идентификатор записи
     * @param updatedSpecialtyDTO - DTO обновлённой специальности
     */
    @Transactional
    @Override
    public void update(Long id, SpecialtyDTO updatedSpecialtyDTO) {
        if (specialtyRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Specialty not found");
        }
        Specialty updatedSpecialty = mappingUtils.mapToSpecialty(updatedSpecialtyDTO);
        updatedSpecialty.setId(id);
        specialtyRepository.save(updatedSpecialty);
    }

    /**
     * Функция удаления существующей записи из таблицы специальностей в базе даннных по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (specialtyRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Specialty not found");
        }
        specialtyRepository.deleteById(id);
    }
}
