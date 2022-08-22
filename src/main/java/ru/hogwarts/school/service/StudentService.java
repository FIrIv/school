package ru.hogwarts.school.service;

import static java.lang.Thread.sleep;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static ru.hogwarts.school.exception.AvatarNotFoundException.avatarNotFoundException;
import static ru.hogwarts.school.exception.StudentNotFoundException.studentNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student createStudent (Student student) {
        logger.info("Was invoked method for create student. ");
        if (student == null) {
            logger.error("There is no student to add. ");
            return null;
        }
        return studentRepository.save(student);
    }

    public Student readStudent (long id) {
        logger.info("Was invoked method for read student with id {}. ", id);
        return studentRepository.findById(id)
                .orElseThrow(studentNotFoundException("Студент с id {} не найден. ", id));
    }

    public Student updateStudent (Student student) {
        logger.info("Was invoked method for update student with id {}. ", student.getId());
        if (student == null) {
            logger.error("There is no student to update. ");
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent (long id) {
        logger.info("Was invoked method for delete student with id {}. ", id);
        studentRepository.deleteById(id);
    }

    public List<Student> readStudentWithAge(int age) {
        logger.info("Was invoked method for read student with age {}. ", age);
        return (List<Student>) studentRepository.findStudentsByAge(age);
    }

    public List<Student> readStudentsByAgeBetween(int ageMin, int ageMax) {
        logger.info("Was invoked method for read student with age between {} and {}. ", ageMin, ageMax);
        return (List<Student>) studentRepository.findStudentsByAgeIsBetween(ageMin, ageMax);
    }

    public Collection<Student> readAllStudents() {
        logger.info("Was invoked method for read all students. ");
        return studentRepository.findAll();
    }

    public Faculty findFacultyByStudentId(Long studentId) {
        logger.info("Was invoked method for read faculty of student with id {}. ", studentId);
        return studentRepository.findStudentById(studentId).getFaculty();
    }

    public Avatar findAvatar(long studentId) {
        logger.info("Was invoked method to get avatar of student with id {}. ", studentId);
        return avatarRepository.findByStudentId(studentId)
                .orElseThrow(avatarNotFoundException("Аватар для студент с id {} не найден. ", studentId));
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method to upload avatar to student with id {}. ", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(studentNotFoundException("Студент с id {} не найден. ", studentId));

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        logger.debug("Try to create buffer... ");
        try (
             InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            logger.debug("It is buffering successful. ");
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        logger.info("Was invoked method to get extension of file. ");
        return fileName.substring(fileName.lastIndexOf(").") + 1);
    }

    public Integer getCountOfStudents() {
        logger.info("Was invoked method to get extension of file. ");
        return studentRepository.getCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method to get average age of students. ");
        //return studentRepository.getAverageAgeOfStudents();
        return studentRepository.findAll()
                .stream().mapToInt(p -> p.getAge())
                .average()
                .orElseThrow(studentNotFoundException("Студенты не найдены. "));
    }

    public Collection<Student> getFiveLastStudents() {
        logger.info("Was invoked method to get last five students. ");
        return studentRepository.getFiveLastStudents();
    }

    public List<Avatar> getAllAvatars(PageRequest pageRequest) {
        logger.info("Was invoked method to get all avatars. ");
        return avatarRepository.findAll(pageRequest).getContent();
    }

    public List<String> readAllStudentsWithNameStartsWith(String letter) {
        logger.info("Was invoked method to get all names of students starts with letters {}. ", letter);
        return studentRepository.findAll()
                .stream()
                .filter(p -> p.getName().toUpperCase().startsWith(letter.toUpperCase()))
                .map(p -> p.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public void testThreads() {
        List<Student> tempListOfStudents = new ArrayList<>();
        for (long i=0L; i<6L; i++) {
            tempListOfStudents.add(studentRepository.findStudentById(i));
        }

        System.out.println();
        System.out.println("Потоки: ");
        System.out.println();

        System.out.println(tempListOfStudents.get(0));
        pause(3_000);
        System.out.println(tempListOfStudents.get(1));
        pause(3_000);

        new Thread (() -> {
            System.out.println(tempListOfStudents.get(2));
            pause(3_000);
            System.out.println(tempListOfStudents.get(3));
            pause(3_000);
        }).start();

        new Thread (() -> {
            System.out.println(tempListOfStudents.get(4));
            pause(3_000);
            System.out.println(tempListOfStudents.get(5));
            pause(3_000);
        }).start();
    }

    private void pause (int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void testSynchronizedThreads() throws InterruptedException {
        System.out.println();
        System.out.println("Синхронизированные потоки: ");
        System.out.println();

        List<Student> tempListOfStudents = new ArrayList<>();
        for (long i=0L; i<6L; i++) {
            tempListOfStudents.add(studentRepository.findStudentById(i));
        }

        printName(tempListOfStudents.subList(0, 1+1));

        new Thread (() -> {
                printName(tempListOfStudents.subList(2, 3+1));
        }).start();

        new Thread (() -> {
                printName(tempListOfStudents.subList(4, 5+1));
        }).start();
    }

    private synchronized void printName (List<Student> sublistOfStudents) {
        for (int i=0; i<sublistOfStudents.size(); i++) {
            System.out.println(sublistOfStudents.get(i));
        }
        pause(3000);
    }
}
