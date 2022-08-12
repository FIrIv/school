package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;
import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Факультет отсутствует в базе. ")
public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException (String message) {
        super(message);
    }

    public FacultyNotFoundException (String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<FacultyNotFoundException> facultyNotFoundException (String message) {
        return () -> new FacultyNotFoundException(message);
    }

    public static Supplier<FacultyNotFoundException> facultyNotFoundException (String message, Object... args) {
        return () -> new FacultyNotFoundException(message, args);
    }
}


