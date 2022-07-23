package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping        // CREATE
    public ResponseEntity<Faculty> createFaculty (@RequestBody Faculty faculty) {
        Faculty newFaculty = facultyService.createFaculty(faculty);
        if (newFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newFaculty);
    }

    @GetMapping("{id}")        // GET
    public ResponseEntity<Faculty> readFaculty (@PathVariable long id) {
        Faculty faculty = facultyService.readFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping      // GET
    public ResponseEntity<Collection<Faculty>> readAllOrFilterFaculties (@RequestParam(required = false) String color,
                                                                         @RequestParam(required = false) String name) {
        Collection<Faculty> faculties;
        if (color!= null) {
            faculties = facultyService.readFacultyWithColor(color);
        } else if (name != null) {
            faculties = facultyService.readFacultyWithName(name);
        } else {
            faculties = facultyService.readAllFaculties();
        }
        if (faculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping      // GET
    public ResponseEntity<Collection<Student>> findStudentsByFaculty (@RequestParam (name = "faculty_id", required = false) Long id,
                                                                      @RequestParam (name = "faculty_name", required = false) String name) {
        Set<Student> students = null;
        if (id != null) {
            students = facultyService.findStudentsByFacultyId(id);
        } else if (name != null && !name.isBlank()) {
            students = facultyService.findStudentsByFacultyName(name);
        }
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @PutMapping     // UPDATE
    public ResponseEntity<Faculty> updateFaculty (@RequestBody Faculty faculty) {
        Faculty newFaculty = facultyService.updateFaculty(faculty);
        if (newFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newFaculty);
    }

    @DeleteMapping ("{id}")     // DELETE
    public ResponseEntity deleteFaculty (@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
