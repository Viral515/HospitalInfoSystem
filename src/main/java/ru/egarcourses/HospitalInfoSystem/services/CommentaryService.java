package ru.egarcourses.HospitalInfoSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;
import ru.egarcourses.HospitalInfoSystem.repositories.CommentaryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentaryService {

    private final CommentaryRepository commentaryRepository;

    @Autowired
    public CommentaryService(CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
    }

    public List<Commentary> findAll() {
        return commentaryRepository.findAll();
    }

    public Commentary findById(int id) {
        Optional<Commentary> foundCommentary = commentaryRepository.findById(id);
        return foundCommentary.orElse(null);
    }

    @Transactional
    public void save(Commentary commentary) {
        commentaryRepository.save(commentary);
    }

    @Transactional
    public void update(int id, Commentary updatedCommentary) {
        updatedCommentary.setId(id);
        commentaryRepository.save(updatedCommentary);
    }

    @Transactional
    public void delete(int id) {
        commentaryRepository.deleteById(id);
    }
}
