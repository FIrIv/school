package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Аватар отсутствует в базе. ")
public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException () {}
}


