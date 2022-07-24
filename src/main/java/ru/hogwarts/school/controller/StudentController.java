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
    public ResponseEntity<Student> readStudent (@RequestParam Long id) {
        Student student = null;
        if (id != null) {
            student = studentService.readStudent(id);
        }
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age")      // GET
    public ResponseEntity<Collection<Student>> readAllOrFilterStudents (@RequestParam Integer age) {
        Collection<Student> students = null;
        if (age != null) {
            students = studentService.readStudentWithAge(age);
        } else {
            students = studentService.readAllStudents();
        }
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/agebetween")      // GET
    public ResponseEntity<Collection<Student>> readAllOrFilterStudents (@RequestParam(name = "minage") Integer ageMin,
                                                                        @RequestParam(name = "maxage") Integer ageMax) {
        Collection<Student> students = null;
        if (ageMin != null && ageMax != null) {
            students = studentService.readStudentsByAgeBetween(ageMin, ageMax);
        } else {
            students = studentService.readAllStudents();
        }
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping ("/faculty")    // GET
    public ResponseEntity<Faculty> readFacultyByStudentId (@RequestParam(name = "student_id") Long id) {
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
    public ResponseEntity deleteStudent (@RequestParam long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
