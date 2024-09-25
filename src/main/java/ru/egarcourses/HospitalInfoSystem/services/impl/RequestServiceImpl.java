package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.models.Request;
import ru.egarcourses.HospitalInfoSystem.repositories.RequestRepository;
import ru.egarcourses.HospitalInfoSystem.services.RequestService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализующий интерфейс сервиса для работы с записями на приём
 */
@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    /**
     * Поле хранящее количество максимальных записей на день у врача
     */
    private static final int MAX_REQUEST_IN_DAY_COUNT = 20;

    /**
     * Поле репозитория записи на приём для работы с базой данных
     */
    private final RequestRepository requestRepository;
    /**
     * Поле маппера сущностей в DTO и обратно
     */
    private final MappingUtils mappingUtils;

    /**
     * Конструктор - создаёт новый объект сервиса работы с записями на приём
     *
     * @param requestRepository - объект репозитория записи на приём
     * @param mappingUtils      - объект маппера сущностей
     */
    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, MappingUtils mappingUtils) {
        this.requestRepository = requestRepository;
        this.mappingUtils = mappingUtils;
    }

    /**
     * Функция получения списка всех записей из таблицы записей на приём в базе данных
     *
     * @return возврает список DTO записей на приём
     */
    @Override
    public List<RequestDTO> findAll() {
        List<Request> requests = requestRepository.findAll();
        return requests.stream().map(mappingUtils::mapToRequestDTO).collect(Collectors.toList());
    }

    /**
     * Функция получения записи из таблицы специальности в базе данных по заданному id
     *
     * @param id - уникальный идентификатор записи
     * @return возвращает DTO найденной записи на приём
     */
    @Override
    public RequestDTO findById(Long id) {
        Optional<Request> foundRequest = requestRepository.findById(id);
        if (!foundRequest.isPresent()) {
            throw new NotFoundException("Request not found");
        }
        return mappingUtils.mapToRequestDTO(foundRequest.get());
    }

    /**
     * Функция сохранения новой записи в таблице записей на приём в базе данных
     *
     * @param requestDTO - DTO новой записи
     */
    @Transactional
    @Override
    public void save(RequestDTO requestDTO) {
        UpdateAllTimes(requestDTO, true);
    }

    /**
     * Функция обновления существующей записи в таблице специальностей в базе данных по заданному id
     *
     * @param id                - уникальный идентификатор записи
     * @param updatedRequestDTO - DTO обновлённой записи на приём
     */
    @Transactional
    @Override
    public void update(Long id, RequestDTO updatedRequestDTO) {
        if (!requestRepository.findById(id).isPresent()) {
            throw new NotFoundException("Request not found");
        }
        updatedRequestDTO.setId(id);
        UpdateAllTimes(updatedRequestDTO, false);
    }

    /**
     * Функция удаления существующей записи в таблице записей на приём по заданному id
     *
     * @param id - уникальный идентификатор записи
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (!requestRepository.findById(id).isPresent()) {
            throw new NotFoundException("Request not found");
        }
        requestRepository.deleteById(id);
    }

    /**
     * Функция сохранения новой записи на день и изменения времени посещения у всех остальных
     * записей на этот день
     *
     * @param requestDTO - DTO новой записи на приём
     * @param save       - параметр для обозначения типа функции, в которой метод был вызван.
     *                   "true" для метода "save", "false" для метода "update"
     */
    private void UpdateAllTimes(RequestDTO requestDTO, boolean save) {
        Request updatedRequest = mappingUtils.mapToRequest(requestDTO);
        List<Request> requests = requestRepository.findAllByDoctorIdAndDesiredDate(updatedRequest.getDoctor().getId(),
                updatedRequest.getDesiredDate());
        int requestsPerDayToDoctorCount = requests.size();
        if (requestsPerDayToDoctorCount > MAX_REQUEST_IN_DAY_COUNT && save) {
            throw new NotCreatedException("Requests not created. The schedule for this day is full.");
        }
        if (requestsPerDayToDoctorCount > MAX_REQUEST_IN_DAY_COUNT && !save) {
            throw new NotUpdatedException("Requests not updated. The schedule for this day is full.");
        }
        int i = 0;
        requests.add(updatedRequest);
        for (Request request : requests) {
            int hours = ((i * 15) / 60) + 8;
            int minutes = (i * 15) % 60;
            request.setApprovedDate(updatedRequest.getDesiredDate().atTime(hours, minutes));
            requestRepository.save(request);
            i++;
        }
    }
}
