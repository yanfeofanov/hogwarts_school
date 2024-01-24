package ru.hogwarts.school.exception;

public class StudentNotFindException extends RuntimeException {

    private final long id;

    public StudentNotFindException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Студент с id "+id+" не найден!";
    }
}
