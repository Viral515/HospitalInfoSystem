package ru.egarcourses.HospitalInfoSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egarcourses.HospitalInfoSystem.models.Request;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для работы с таблицей записей на приём в базе данных
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    /**
     * Функция поиска записи в таблице записей на приём в базе данных по заданному id доктора и дате
     *
     * @param doctorId    - уникальный идентификатор доктора в записи на приём
     * @param desiredDate - дата приёма
     * @return возвращает список всех записей на приём к конкретному врачу на конкретную дату
     */
    List<Request> findAllByDoctorIdAndDesiredDate(Long doctorId, LocalDate desiredDate);
}
