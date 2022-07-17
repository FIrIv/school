package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private Map<Long, Student> students = new HashMap<>();
    private long counter = 0;

    public Student createStudent (Student student) {
        if (student == null) {
            return null;
        }
        counter++;
        student.setId(counter);
        students.put(counter, student);
        return student;
    }

    public Student readStudent (long id) {
        return students.get(id);
    }

    public Student updateStudent (Student student) {
        if (student == null) {
            return null;
        }
        return students.put(student.getId(), student);
    }

    public Student deleteStudent (long id) {
        return students.remove(id);
    }

    public List<Student> readStudentWithAge(int age) {
        return students.values()
                .stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
    }
}
