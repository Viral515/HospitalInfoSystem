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
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotFoundedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

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
            throw new NotFoundedException("Requests not founded");
        }
        return requestRepository.findAll().stream().map(mappingUtils::mapToRequestDTO).collect(Collectors.toList());
    }

    @Override
    public RequestDTO findById(int id) {
        Optional<Request> foundRequest = requestRepository.findById(id);
        if (!foundRequest.isPresent()) {
            throw new NotFoundedException("Request not founded");
        }
        return mappingUtils.mapToRequestDTO(foundRequest.get());
    }

    @Transactional
    @Override
    public void save(RequestDTO requestDTO) {
        requestRepository.save(mappingUtils.mapToRequest(requestDTO));
        if (!requestRepository.findById(requestDTO.getId()).isPresent()) {
            throw new NotCreatedException("Request not created");
        }
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
            throw new NotFoundedException("Request not founded");
        }
        requestRepository.deleteById(id);
    }
}
