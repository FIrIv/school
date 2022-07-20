package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty (Faculty faculty) {
        if (faculty == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty (long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty (Faculty faculty) {
        if (faculty == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty (long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> readFacultyWithAge(String color) {
        return facultyRepository.findAll().stream()
                .filter(e -> e.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> readAllFaculties() {
        return facultyRepository.findAll();
    }
}
