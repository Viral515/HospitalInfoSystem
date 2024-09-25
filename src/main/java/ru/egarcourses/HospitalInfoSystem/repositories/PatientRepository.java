package ru.egarcourses.HospitalInfoSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egarcourses.HospitalInfoSystem.models.Patient;

/**
 * Репозиторий для работы с таблицей пациентов в базе данных
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
