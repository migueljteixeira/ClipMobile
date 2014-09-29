package com.migueljteixeira.clipmobile.entities;

import java.util.LinkedList;
import java.util.List;

public class Student extends Entity {

    private String numberId; // number on URL
    private String number;   // real student number
    private List<StudentYear> years;
    /*private List<StudentScheduleDay> scheduleDays;
    private List<StudentCalendarAppointment> calendarAppointments;
    private List<StudentClasses> classes;
    private List<StudentDocs> docs;*/

    public Student(){
        this.years = new LinkedList<StudentYear>();
        //this.scheduleDays = new ArrayList<StudentScheduleDay>(8);
    }

    /*public Student(long id, String student_number_id, String student_number) {
        this.id = id;
        this.numberID = student_number_id;
        this.number = student_number;
        this.scheduleDays = new ArrayList<StudentScheduleDay>(8);

        for(int i = 0; i<8; i++)
            scheduleDays.add(null);
    }*/

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String student_number_id) {
        this.numberId = student_number_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String student_number) {
        this.number = student_number;
    }

    public List<StudentYear> getYears() {
        return years;
    }

    public void setYears(List<StudentYear> years) {
        this.years = years;
    }

    public void addYear(StudentYear year) {
        years.add(year);
    }

    public boolean hasStudentYears() {
        return !years.isEmpty();
    }

    /*
    public StudentScheduleDay getScheduleDay(int pos) {
        return scheduleDays.get(pos);
    }

    public void addScheduleDay(int dayPos, StudentScheduleDay day) {
        scheduleDays.set(dayPos, day);
    }

    public List<StudentScheduleDay> getScheduleDays() {
        return scheduleDays;
    }

    public List<StudentCalendarAppointment> getCalendarAppointments() {
        return calendarAppointments;
    }

    public void setCalendarAppointments(List<StudentCalendarAppointment> calendarAppointments) {
        this.calendarAppointments = calendarAppointments;
    }

    public List<StudentClasses> getClasses() {
        return classes;
    }

    public void setClasses(List<StudentClasses> classes) {
        this.classes = classes;
    }

    public List<StudentDocs> getDocs() {
        return docs;
    }

    public void setDocs(List<StudentDocs> docs) {
        this.docs = docs;
    }*/
}
