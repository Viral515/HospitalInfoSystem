package ru.egarcourses.HospitalInfoSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egarcourses.HospitalInfoSystem.models.Doctor;

/**
 * Репозиторий для работы с таблицей докторов в базе данных
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
