package ru.egarcourses.HospitalInfoSystem.utils.exceptions;

/**
 * Класс ошибки обновления записи в базе данных
 */
public class NotUpdatedException extends RuntimeException {
    /**
     * Конструктор - создание новой ошибки с описанием
     *
     * @param message - сообщение с описанием ошибки
     */
    public NotUpdatedException(String message) {
        super(message);
    }
}
