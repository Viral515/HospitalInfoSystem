package ru.egarcourses.HospitalInfoSystem.utils.exceptions;

/**
 * Класс ошибки создания записи в базе данных
 */
public class NotCreatedException extends RuntimeException {

    /**
     * Конструктор - создание новой ошибки с описанием
     *
     * @param message - сообщение с описанием ошибки
     */
    public NotCreatedException(String message) {
        super(message);
    }
}
