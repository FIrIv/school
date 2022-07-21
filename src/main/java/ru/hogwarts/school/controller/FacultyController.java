package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping        // CREATE
    public ResponseEntity<Faculty> createFaculty (Faculty faculty) {
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
    public ResponseEntity<Collection<Faculty>> readAllFaculties () {
        Collection<Faculty> faculties = facultyService.readAllFaculties();
        if (faculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping ("/filter")        // GET
    public ResponseEntity<List<Faculty>> readFacultyWithColor (@RequestParam("color") String color) {
        List<Faculty> faculties = facultyService.readFacultyWithAge(color);
        if (faculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @PutMapping     // UPDATE
    public ResponseEntity<Faculty> updateStudent (Faculty faculty) {
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
