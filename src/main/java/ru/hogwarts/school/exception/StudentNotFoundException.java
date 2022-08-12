package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;
import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cтудент отсутствует в базе. ")
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException (String message) {
        super(message);
    }

    public StudentNotFoundException (String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<StudentNotFoundException> studentNotFoundException (String message) {
        return () -> new StudentNotFoundException(message);
    }

    public static Supplier<StudentNotFoundException> studentNotFoundException (String message, Object... args) {
        return () -> new StudentNotFoundException(message, args);
    }
}

