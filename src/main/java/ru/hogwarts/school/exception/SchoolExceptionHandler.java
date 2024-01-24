package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SchoolExceptionHandler {

    Logger logger = LoggerFactory.getLogger(SchoolExceptionHandler.class);

    @ExceptionHandler({
            FacultyNotFindException.class,
            StudentNotFindException.class,
            AvatarNotFindException.class
    })
    public ResponseEntity<?> handleNotFound(RuntimeException e){
        logger.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler({AvatarProcessingException.class})
    public ResponseEntity<?> handleInternalServerError(RuntimeException e){
        logger.error("Exception handleInternalServerError ");
        return ResponseEntity.internalServerError().build();
    }


}
