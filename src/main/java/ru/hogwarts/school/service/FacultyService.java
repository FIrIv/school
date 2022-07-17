package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();
    private long counter = 0;

    public Faculty createStudent (Faculty faculty) {
        if (faculty == null) {
            return null;
        }
        counter++;
        faculty.setId(counter);
        faculties.put(counter, faculty);
        return faculty;
    }

    public Faculty readStudent (long id) {
        return faculties.get(id);
    }

    public Faculty updateStudent (Faculty faculty) {
        if (faculty == null) {
            return null;
        }
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty deleteStudent (long id) {
        return faculties.remove(id);
    }

    public List<Faculty> readFacultyWithAge(String color) {
        return faculties.values()
                .stream()
                .filter(e -> e.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
