package com.migueljteixeira.clipmobile.entities;

import java.util.LinkedList;
import java.util.List;

public class User extends Entity {
    private List<Student> students;

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
}
