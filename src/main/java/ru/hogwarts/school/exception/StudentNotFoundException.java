package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cтудент отсутствует в базе. ")
public class StudentNotFoundException {
    public StudentNotFoundException () {}
}

