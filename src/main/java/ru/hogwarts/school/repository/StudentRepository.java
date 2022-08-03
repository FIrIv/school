package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAge (int age);
    Collection<Student> findStudentsByAgeIsBetween(int minAge, int maxAge);

    Student findStudentById(Long id);

    @Query(value = "SELECT COUNT (*) FROM student", nativeQuery = true)
    Integer getCountOfStudents();

    // пусть округляет средний возраст до целого
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudents();
}
