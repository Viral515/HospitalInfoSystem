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

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private static final int MAX_REQUEST_IN_DAY_COUNT = 20;

    private final RequestRepository requestRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, MappingUtils mappingUtils) {
        this.requestRepository = requestRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    public List<RequestDTO> findAll() {
        List<Request> requests = requestRepository.findAll();
        if (requests.isEmpty()) {
            throw new NotFoundException("Requests not founded");
        }
        return requests.stream().map(mappingUtils::mapToRequestDTO).collect(Collectors.toList());
    }

    @Override
    public RequestDTO findById(Long id) {
        Optional<Request> foundRequest = requestRepository.findById(id);
        if (!foundRequest.isPresent()) {
            throw new NotFoundException("Request not found");
        }
        return mappingUtils.mapToRequestDTO(foundRequest.get());
    }

    @Transactional
    @Override
    public void save(RequestDTO requestDTO) {
        UpdateAllTimes(requestDTO, true);
    }

    @Transactional
    @Override
    public void update(Long id, RequestDTO updatedRequestDTO) {
        if (!requestRepository.findById(id).isPresent()) {
            throw new NotFoundException("Request not found");
        }
        updatedRequestDTO.setId(id);
        UpdateAllTimes(updatedRequestDTO, false);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!requestRepository.findById(id).isPresent()) {
            throw new NotFoundException("Request not found");
        }
        requestRepository.deleteById(id);
    }

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
        for(Request request : requests) {
            int hours = ((i * 15) / 60) + 8;
            int minutes = (i * 15) % 60;
            request.setApprovedDate(updatedRequest.getDesiredDate().atTime(hours, minutes));
            requestRepository.save(request);
            i++;
        }
    }
}
