package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.hogwarts.school.exception.FacultyNotFoundException.facultyNotFoundException;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty (Faculty faculty) {
        logger.info("Was invoked method for create faculty. ");
        if (faculty == null) {
            logger.error("There is no faculty to add. ");
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty (long id) {
        logger.info("Was invoked method to read faculty with id {}. ", id);
        return facultyRepository.findById(id)
                .orElseThrow(facultyNotFoundException("Факультет с id {} не найден. ", id));
    }

    public Faculty updateFaculty (Faculty faculty) {
        logger.info("Was invoked method to update faculty with id {}. ", faculty.getId());
        if (faculty == null) {
            logger.error("There is no faculty to update. ");
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty (long id) {
        logger.info("Was invoked method to delete faculty with id {}. ", id);
        facultyRepository.deleteById(id);
    }

    public List<Faculty> readFacultyWithColor(String color) {
        logger.info("Was invoked method to read faculty with color {}. ", color);
        return (List<Faculty>) facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

    public Collection<Faculty> readAllFaculties() {
        logger.info("Was invoked method to read all faculties. ");
        return facultyRepository.findAll();
    }

    public List<Faculty> readFacultyWithName(String name) {
        logger.info("Was invoked method to read faculties with name {}. ", name);
        return (List<Faculty>) facultyRepository.findFacultiesByNameIgnoreCase(name);
    }

    public Set<Student> findStudentsByFacultyId(Long facultyId) {
        logger.info("Was invoked method to read all sttudnts by faculty id {}. ", facultyId);
        return facultyRepository.findFacultyById(facultyId).getStudents();
    }

    public Set<Student> findStudentsByFacultyName(String facultyName) {
        logger.info("Was invoked method to read all students by faculty name {}. ", facultyName);
        return facultyRepository.findFacultyByName(facultyName).getStudents();
    }

    public Set<Faculty> findLongestNameOfFaculty() {
        logger.info("Was invoked method to find the longest names of faculties. ");
        int maxLengthOfName = facultyRepository.findAll()
                .stream()
                .mapToInt(p -> p.getName().length())
                .max()
                .orElseThrow(facultyNotFoundException("Факультеты не найдены. "));
        return facultyRepository.findAll()
                .stream()
                .filter(p -> p.getName().length() == maxLengthOfName)
                .collect(Collectors.toSet());
    }
}
