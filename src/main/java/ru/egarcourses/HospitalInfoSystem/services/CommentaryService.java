package ru.egarcourses.HospitalInfoSystem.services;

import ru.egarcourses.HospitalInfoSystem.dto.CommentaryDTO;

import java.util.List;

public interface CommentaryService {

    public List<CommentaryDTO> findAll();

    public CommentaryDTO findById(int id);

    public void save(CommentaryDTO commentaryDTO);

    public void update(int id, CommentaryDTO updatedCommentaryDTO);

    public void delete(int id);
}
