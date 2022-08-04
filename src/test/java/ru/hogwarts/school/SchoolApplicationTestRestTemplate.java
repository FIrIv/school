package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setAge(15);
        student.setName("IronXXX");

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("IronXXX");
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(15);

        studentController.deleteStudent(response.getBody().getId());
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student expected = new Student();
        expected.setName("1234567890");
        expected.setAge(12345);
        long id = studentController.createStudent(expected).getBody().getId();

        Student student = restTemplate.getForObject("/student/{id}", Student.class, id);
        org.junit.jupiter.api.Assertions
                  .assertEquals(expected.getName(), student.getName());

        studentController.deleteStudent(id);
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        int age = 12346;

        Student expected1 = new Student();
        expected1.setName("Manka");
        expected1.setAge(age);
        Student expected2 = new Student();
        expected2.setName("Anka");
        expected2.setAge(age);
        long id1 = studentController.createStudent(expected1).getBody().getId();
        long id2 = studentController.createStudent(expected2).getBody().getId();


        ResponseEntity<List<Student>> response = restTemplate.exchange("/student/age?age="+age, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);
        Assertions.assertThat(response.getBody().get(0).getName()).isEqualTo("Manka");
        Assertions.assertThat(response.getBody().get(1).getName()).isEqualTo("Anka");

        studentController.deleteStudent(id1);
        studentController.deleteStudent(id2);
    }

    @Test
    public void testGetStudentsBetweenAge1Age2() throws Exception {
        int age1 = 12340;
        int age2 = 12350;

        Student expected1 = new Student();
        expected1.setName("Manka");
        expected1.setAge(12341);
        Student expected2 = new Student();
        expected2.setName("Anka");
        expected2.setAge(12342);
        long id1 = studentController.createStudent(expected1).getBody().getId();
        long id2 = studentController.createStudent(expected2).getBody().getId();


        ResponseEntity<List<Student>> response = restTemplate.exchange("/student/agebetween?minage="+age1+"&maxage="+age2, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);
        Assertions.assertThat(response.getBody().get(0).getName()).isEqualTo("Manka");
        Assertions.assertThat(response.getBody().get(1).getName()).isEqualTo("Anka");

        studentController.deleteStudent(id1);
        studentController.deleteStudent(id2);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setAge(15);
        student.setName("IronXXX");
        long id = studentController.createStudent(student).getBody().getId();

        Student studentUp = new Student();
        studentUp.setAge(15);
        studentUp.setName("IronXXX222");
        studentUp.setId(id);
        HttpEntity<Student> entityUp = new HttpEntity<Student>(studentUp);

        ResponseEntity<Student> response = restTemplate.exchange("/student", HttpMethod.PUT, entityUp,
                Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("IronXXX222");
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(15);

        studentController.deleteStudent(id);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setAge(15);
        student.setName("IronXXX");
        long id = studentController.createStudent(student).getBody().getId();

        ResponseEntity<Student> response = restTemplate.exchange("/student/{id}", HttpMethod.DELETE, null,
                Student.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void testCountStudents() throws Exception {
        int count = studentController.readStudents().getBody().size();

        ResponseEntity<Integer> response = restTemplate.exchange("/student/count", HttpMethod.GET, null,
                Integer.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(count);
    }

    @Test
    public void testGetAverageAgeOfStudents() throws Exception {
        int count = studentController.readStudents().getBody().size();
        double sum = 0;
        List<Student> students = studentController.readStudents().getBody().stream().toList();
        for (int i=0; i<count; i++) {
            sum += students.get(i).getAge();
        }
        double avAge = sum / (double)count;

        ResponseEntity<Double> response = restTemplate.exchange("/student/age/average", HttpMethod.GET, null,
                Double.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(avAge);
    }
}
