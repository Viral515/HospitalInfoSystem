package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.repositories.CommentaryRepository;
import ru.egarcourses.HospitalInfoSystem.services.CommentaryService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommentaryServiceImpl implements CommentaryService {

    private final CommentaryRepository commentaryRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public CommentaryServiceImpl(CommentaryRepository commentaryRepository, MappingUtils mappingUtils) {
        this.commentaryRepository = commentaryRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    public List<CommentaryDTO> findAll() {
        return commentaryRepository.findAll().stream().map(mappingUtils::mapToCommentaryDTO).collect(Collectors.toList());
    }

    @Override
    public CommentaryDTO findById(int id) {
        Optional<Commentary> foundCommentary = commentaryRepository.findById(id);
        return mappingUtils.mapToCommentaryDTO(foundCommentary.get());
    }

    @Transactional
    @Override
    public void save(CommentaryDTO commentaryDTO) {
        commentaryRepository.save(mappingUtils.mapToCommentary(commentaryDTO));
    }

    @Transactional
    @Override
    public void update(int id, CommentaryDTO updatedCommentaryDTO) {
        Commentary updatedCommentary = mappingUtils.mapToCommentary(updatedCommentaryDTO);
        updatedCommentary.setId(id);
        commentaryRepository.save(updatedCommentary);
    }

    @Transactional
    @Override
    public void delete(int id) {
        commentaryRepository.deleteById(id);
    }
}
