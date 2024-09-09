package ru.egarcourses.HospitalInfoSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.models.Request;
import ru.egarcourses.HospitalInfoSystem.repositories.RequestRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    public Request findById(int id) {
        Optional<Request> foundRequest= requestRepository.findById(id);
        return foundRequest.orElse(null);
    }

    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Transactional
    public void update(int id, Request updatedRequest) {
        updatedRequest.setId(id);
        requestRepository.save(updatedRequest);
    }

    @Transactional
    public void delete(int id) {
        requestRepository.deleteById(id);
    }
}
