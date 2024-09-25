package ru.egarcourses.HospitalInfoSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egarcourses.HospitalInfoSystem.models.Commentary;

/**
 * Репозиторий для работы с таблицей комментариев в базе данных
 */
@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}
