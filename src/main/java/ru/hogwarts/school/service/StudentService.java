package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        return studentRepository.findById(id).get();
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
        return studentRepository.findAll()
                .stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> readAllStudents() {
        return studentRepository.findAll();
    }
}
