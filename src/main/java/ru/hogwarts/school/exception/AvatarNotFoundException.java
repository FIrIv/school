package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;
import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Аватар отсутствует в базе. ")
public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException (String message) {
        super(message);
    }

    public AvatarNotFoundException (String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<AvatarNotFoundException> avatarNotFoundException (String message) {
        return () -> new AvatarNotFoundException(message);
    }

    public static Supplier<AvatarNotFoundException> avatarNotFoundException (String message, Object... args) {
        return () -> new AvatarNotFoundException(message, args);
    }
}


