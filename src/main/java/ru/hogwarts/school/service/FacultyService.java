package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        return facultyRepository.findById(id).orElseThrow();
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

    public List<Faculty> readFacultyWithColor(String color) {
        return (List<Faculty>) facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

    public Collection<Faculty> readAllFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> readFacultyWithName(String name) {
        return (List<Faculty>) facultyRepository.findFacultiesByNameIgnoreCase(name);
    }

    public Set<Student> findStudentsByFacultyId(Long facultyId) {
        return facultyRepository.findFacultyById(facultyId).getStudents();
    }

    public Set<Student> findStudentsByFacultyName(String facultyName) {
        return facultyRepository.findFacultyByName(facultyName).getStudents();
    }
}
