package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping        // CREATE
    public ResponseEntity<Student> createStudent (@RequestBody Student student) {
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
    public ResponseEntity<Collection<Student>> readAllOrFilterStudents (@RequestParam(required = false) Integer age,
                                                                        @RequestParam(name = "minage", required = false) Integer ageMin,
                                                                        @RequestParam(name = "maxage", required = false) Integer ageMax) {
        Collection<Student> students;
        if (age != null) {
            students = studentService.readStudentWithAge(age);
        } else if (ageMin != null && ageMax != null) {
            students = studentService.readStudentsByAgeBetween(ageMin, ageMax);
        } else {
            students = studentService.readAllStudents();
        }
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping     // GET
    public ResponseEntity<Faculty> readFacultyByStudentId (@RequestParam("student_id") Long id) {
        Faculty faculty = null;
        if (id != null) {
            faculty = studentService.findFacultyByStudentId(id);
        }
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping     // UPDATE
    public ResponseEntity<Student> updateStudent (@RequestBody Student student) {
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
