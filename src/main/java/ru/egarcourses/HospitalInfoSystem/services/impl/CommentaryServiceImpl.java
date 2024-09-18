package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.repositories.CommentaryRepository;
import ru.egarcourses.HospitalInfoSystem.services.CommentaryService;
import ru.egarcourses.HospitalInfoSystem.util.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotCreatedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotFoundedException;
import ru.egarcourses.HospitalInfoSystem.util.exceptions.NotUpdatedException;

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
        List<Commentary> commentaryList = commentaryRepository.findAll();
        if (commentaryList.isEmpty()) {
            throw new NotFoundedException("Commentaries not founded");
        }
        return commentaryList.stream().map(mappingUtils::mapToCommentaryDTO).collect(Collectors.toList());
    }

    @Override
    public CommentaryDTO findById(int id) {
        Optional<Commentary> foundCommentary = commentaryRepository.findById(id);
        if (!foundCommentary.isPresent()) {
            throw new NotFoundedException("Commentary not founded");
        }
        return mappingUtils.mapToCommentaryDTO(foundCommentary.get());
    }

    @Transactional
    @Override
    public void save(CommentaryDTO commentaryDTO) {
        commentaryRepository.save(mappingUtils.mapToCommentary(commentaryDTO));
        if (!commentaryRepository.findById(commentaryDTO.getId()).isPresent()) {
            throw new NotCreatedException("Comment not created");
        }
    }

    @Transactional
    @Override
    public void update(int id, CommentaryDTO updatedCommentaryDTO) {
        Commentary updatedCommentary = mappingUtils.mapToCommentary(updatedCommentaryDTO);
        updatedCommentary.setId(id);
        commentaryRepository.save(updatedCommentary);
        if (!commentaryRepository.findById(id).equals(updatedCommentary)) {
            throw new NotUpdatedException("Comment not updated");
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        if (!commentaryRepository.findById(id).isPresent()) {
            throw new NotFoundedException("Comment not founded");
        }
        commentaryRepository.deleteById(id);
    }
}
