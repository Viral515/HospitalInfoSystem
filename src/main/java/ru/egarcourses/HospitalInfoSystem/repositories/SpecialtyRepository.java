package ru.egarcourses.HospitalInfoSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egarcourses.HospitalInfoSystem.models.Specialty;

/**
 * Репозиторий для работы с таблицей специальностей в базе данных
 */
@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    /**
     * Функция поиска записи в таблице специальностей в базе данных по заданному названию специальности
     *
     * @param name - название специальности
     * @return возвращает объект специальности
     */
    Specialty findByName(String name);
}
