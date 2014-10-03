package com.migueljteixeira.clipmobile.entities;

import java.util.LinkedList;
import java.util.List;

public class User extends Entity {

    private List<Student> students;
    private String name;

    public User() {
        students = new LinkedList<Student>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean hasStudents() {
        return !students.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
