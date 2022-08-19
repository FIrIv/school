package ru.hogwarts.school.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.awt.print.Pageable;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

@RestController
@RequestMapping("/student")
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

    @GetMapping         // GET
    public ResponseEntity<Collection<Student>> readStudents () {
        return ResponseEntity.ok(studentService.readAllStudents());
    }

    @GetMapping ("{id}")        // GET
    public ResponseEntity<Student> readStudent (@PathVariable Long id) {
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
        Collection<Student> students;
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

    @GetMapping("/namestartswith")      // GET
    public ResponseEntity<Collection<Student>> readAllStudentsWithNameStartsWith (@RequestParam String letter) {
        Collection<Student> students;
        if (letter != null) {
            students = studentService.readAllStudentsWithNameStartsWith(letter);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age/between")      // GET
    public ResponseEntity<Collection<Student>> readAllOrFilterStudents (@RequestParam(name = "minage") Integer ageMin,
                                                                        @RequestParam(name = "maxage") Integer ageMax) {
        Collection<Student> students;
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
    public ResponseEntity deleteStudent (@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        studentService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = studentService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = studentService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Integer> getCountOfStudents() {
        return ResponseEntity.ok(studentService.getCountOfStudents());
    }

    @GetMapping(value = "/age/average")
    public ResponseEntity<Double> getAverageAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudents());
    }

    @GetMapping(value = "/lastfive")
    public ResponseEntity<Collection<Student>> getFiveLastStudents() {
        return ResponseEntity.ok(studentService.getFiveLastStudents());
    }

    @GetMapping(value = "/avatar/getAll")
    public ResponseEntity<Collection<Avatar>> getFiveAvatarsByPage (@RequestParam int page) {
        Collection<Avatar> avatars;
        PageRequest pageRequest = PageRequest.of(page-1, 5);
        return ResponseEntity.ok(studentService.getAllAvatars(pageRequest));
    }
}
