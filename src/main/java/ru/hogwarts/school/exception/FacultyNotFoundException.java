package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Факультет отсутствует в базе. ")
public class FacultyNotFoundException {
    public FacultyNotFoundException () {}
}


