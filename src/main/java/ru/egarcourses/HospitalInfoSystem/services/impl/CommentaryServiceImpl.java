package ru.egarcourses.HospitalInfoSystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.repositories.CommentaryRepository;
import ru.egarcourses.HospitalInfoSystem.services.CommentaryService;
import ru.egarcourses.HospitalInfoSystem.utils.MappingUtils;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotFoundException;
import ru.egarcourses.HospitalInfoSystem.utils.exceptions.NotUpdatedException;

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
            throw new NotFoundException("Commentaries not found");
        }
        return commentaryList.stream().map(mappingUtils::mapToCommentaryDTO).collect(Collectors.toList());
    }

    @Override
    public CommentaryDTO findById(Long id) {
        Optional<Commentary> foundCommentary = commentaryRepository.findById(id);
        if (!foundCommentary.isPresent()) {
            throw new NotFoundException("Commentary not found");
        }
        return mappingUtils.mapToCommentaryDTO(foundCommentary.get());
    }

    @Transactional
    @Override
    public void save(CommentaryDTO commentaryDTO) {
//        commentaryDTO.setId(0L);
        commentaryRepository.save(mappingUtils.mapToCommentary(commentaryDTO));
    }

    @Transactional
    @Override
    public void update(Long id, CommentaryDTO updatedCommentaryDTO) {
        Commentary updatedCommentary = mappingUtils.mapToCommentary(updatedCommentaryDTO);
        updatedCommentary.setId(id);
        commentaryRepository.save(updatedCommentary);
        if (commentaryRepository.findById(id).equals(updatedCommentary)) {
            throw new NotUpdatedException("Comment not updated");
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!commentaryRepository.findById(id).isPresent()) {
            throw new NotFoundException("Comment not found");
        }
        commentaryRepository.deleteById(id);
    }
}
