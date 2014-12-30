package com.migueljteixeira.clipmobile.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Student extends Entity {

    private String numberId; // number on URL
    private String number;   // real student number
    private List<StudentYearSemester> years;
    private Map<Integer, List<StudentScheduleClass>> scheduleClasses; // <semester, scheduleClasses>
    private Map<Integer, List<StudentClass>> studentClasses; // <semester, classes>
    private Map<Boolean, List<StudentCalendar>> studentCalendar; // <isExam, calendar>
    private List<StudentClassDoc> studentClassesDocs;


    /*private List<StudentScheduleDay> scheduleDays;
    private List<StudentCalendarAppointment> calendarAppointments;
    private List<StudentClasses> classes;
    private List<StudentDocs> docs;*/

    public Student(){
        this.years = new LinkedList<StudentYearSemester>();
        this.scheduleClasses = new HashMap<Integer, List<StudentScheduleClass>>(5);
        this.studentClasses = new HashMap<Integer, List<StudentClass>>(2);
        this.studentCalendar = new HashMap<Boolean, List<StudentCalendar>>(2);
        this.studentClassesDocs = new LinkedList<StudentClassDoc>();
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

    public List<StudentYearSemester> getYears() {
        return years;
    }

    public void setYears(List<StudentYearSemester> years) {
        this.years = years;
    }

    public void addYear(StudentYearSemester year) {
        years.add(year);
    }

    public boolean hasStudentYears() {
        return !years.isEmpty();
    }

    public Map<Integer, List<StudentScheduleClass>> getScheduleClasses() {
        return scheduleClasses;
    }

    public void addScheduleClass(int day, StudentScheduleClass scheduleClass) {
        List<StudentScheduleClass> classes = this.scheduleClasses.get(day);
        if(classes == null)
            classes = new LinkedList<StudentScheduleClass>();

        classes.add(scheduleClass);

        System.out.println("--!!! dia: " + day + " , " + classes.size());

        this.scheduleClasses.put(day, classes);
    }

    public Map<Integer, List<StudentClass>> getClasses() {
        return studentClasses;
    }

/*    public void setClasses(List<StudentClass> classes) {
        this.studentClasses = classes;
    }*/

    public void addStudentClass(int semester, StudentClass scheduleClass) {
        List<StudentClass> classes = this.studentClasses.get(semester);
        if(classes == null)
            classes = new LinkedList<StudentClass>();

        classes.add(scheduleClass);

        this.studentClasses.put(semester, classes);
    }

    public List<StudentClassDoc> getClassesDocs() {
        return studentClassesDocs;
    }

    /*public void setClassesDocs(List<StudentClassDoc> classDoc) {
        this.studentClassesDocs = classDoc;
    }*/

    public void addClassDoc(StudentClassDoc classDoc) {
        studentClassesDocs.add(classDoc);
    }

    public Map<Boolean, List<StudentCalendar>> getStudentCalendar() {
        return this.studentCalendar;
    }

    public void addStudentCalendarAppointment(boolean isExam, StudentCalendar calendarAppointment) {
        List<StudentCalendar> calendar = this.studentCalendar.get(isExam);
        if(calendar == null)
            calendar = new LinkedList<StudentCalendar>();

        calendar.add(calendarAppointment);

        this.studentCalendar.put(isExam, calendar);
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
