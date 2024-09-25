package ru.egarcourses.HospitalInfoSystem.utils.exceptions;

/**
 * Класс ошибки отсутсвия искомой записи в базе данных
 */
public class NotFoundException extends RuntimeException {
    /**
     * Конструктор - создание новой ошибки с описанием
     * @param message - сообщение с описанием ошибки
     */
    public NotFoundException(String message) {
        super(message);
    }
}
