package ru.hogwarts.school.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String color;

    public Faculty(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Faculty() {}

    @OneToMany
            (mappedBy = "faculty")
    private Set<Student> students;
    public long getId() {
        return id;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    public void setId(Long id) {
        this.id = id;
    }
}