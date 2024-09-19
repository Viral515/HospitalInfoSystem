package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.RequestDTO;
import ru.egarcourses.HospitalInfoSystem.models.Request;
import ru.egarcourses.HospitalInfoSystem.repositories.RequestRepository;
import ru.egarcourses.HospitalInfoSystem.services.RequestService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private static final int MAX_REQUEST_IN_DAY_COUNT = 9;

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
    public RequestDTO findById(int id) {
        Optional<Request> foundRequest = requestRepository.findById(id);
        if (!foundRequest.isPresent()) {
            throw new NotFoundException("Request not found");
        }
        return mappingUtils.mapToRequestDTO(foundRequest.get());
    }

    public List<RequestDTO> findAllByDoctorIdAndDesiredDate(int doctorId, LocalDate desiredDate) {
        List<Request> requests = requestRepository.findAllByDoctorIdAndDesiredDate(doctorId, desiredDate);
        if (requests.isEmpty()) {
            throw new NotFoundException("Requests not found");
        }
        return requests.stream().map(mappingUtils::mapToRequestDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(RequestDTO requestDTO) {
        List<Request> requests = requestRepository.findAllByDoctorIdAndDesiredDate( requestDTO.getDoctorId(),
                requestDTO.getDesiredDate());
        if (requests.stream().count()> MAX_REQUEST_IN_DAY_COUNT)
        {
            throw new NotCreatedException("Requests not created. The schedule for this day is full.");
        }
        requestRepository.save(mappingUtils.mapToRequest(requestDTO));
    }

    @Transactional
    @Override
    public void update(int id, RequestDTO updatedRequestDTO) {
        Request updatedRequest = mappingUtils.mapToRequest(updatedRequestDTO);
        updatedRequest.setId(id);
        requestRepository.save(updatedRequest);
        if (!requestRepository.findById(id).equals(updatedRequest)) {
            throw new NotUpdatedException("Request not updated");
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        if (!requestRepository.findById(id).isPresent()) {
            throw new NotFoundException("Request not found");
        }
        requestRepository.deleteById(id);
    }
}
