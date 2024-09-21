package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;

import java.util.List;

public interface CommentaryService {

    public List<CommentaryDTO> findAll();

    public CommentaryDTO findById(Long id);

    public void save(CommentaryDTO commentaryDTO);

    public void update(Long id, CommentaryDTO updatedCommentaryDTO);

    public void delete(Long id);
}
