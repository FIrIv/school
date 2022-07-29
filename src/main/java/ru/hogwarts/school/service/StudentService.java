package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent (Student student) {
        if (student == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Student readStudent (long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student updateStudent (Student student) {
        if (student == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent (long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> readStudentWithAge(int age) {
        return (List<Student>) studentRepository.findStudentsByAge(age);
    }

    public List<Student> readStudentsByAgeBetween(int ageMin, int ageMax) {
        return (List<Student>) studentRepository.findStudentsByAgeIsBetween(ageMin, ageMax);
    }

    public Collection<Student> readAllStudents() {
        return studentRepository.findAll();
    }

    public Faculty findFacultyByStudentId(Long studentId) {
        return studentRepository.findStudentById(studentId).getFaculty();
    }
}
