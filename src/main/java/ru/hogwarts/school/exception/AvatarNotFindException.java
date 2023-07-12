package ru.hogwarts.school.exception;

public class AvatarNotFindException extends RuntimeException{
    private final long id;

    public AvatarNotFindException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Аватарка с id "+id+" не найден!";
    }
}
