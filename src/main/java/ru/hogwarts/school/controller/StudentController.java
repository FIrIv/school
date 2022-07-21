package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping        // CREATE
    public ResponseEntity<Student> createStudent (Student student) {
        Student newStudent = studentService.createStudent(student);
        if (newStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping ("{id}")        // GET
    public ResponseEntity<Student> readStudent (@PathVariable long id) {
        Student student = studentService.readStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping      // GET
    public ResponseEntity<Collection<Student>> readAllStudents () {
        Collection<Student> students = studentService.readAllStudents();
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/filter")         // GET
    public ResponseEntity<List<Student>> readStudentWithAge (@RequestParam("age") int age) {
        List<Student> students = studentService.readStudentWithAge(age);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @PutMapping     // UPDATE
    public ResponseEntity<Student> updateStudent (Student student) {
        Student newStudent = studentService.updateStudent(student);
        if (newStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newStudent);
    }

    @DeleteMapping("{id}")      // DELETE
    public ResponseEntity deleteStudent (@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
