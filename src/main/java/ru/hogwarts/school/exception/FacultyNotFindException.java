package ru.hogwarts.school.exception;

public class FacultyNotFindException extends RuntimeException {


    private final long id;

    public FacultyNotFindException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Факультет с id "+id+" не найден!";
    }
}
