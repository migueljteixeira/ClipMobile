package com.migueljteixeira.clipmobile.entities;

public class Student {

    private String id;
    private String numberID; // number on URL
    private String number;   // real student number
    /*private List<String> years;
    private List<StudentScheduleDay> scheduleDays;
    private List<StudentCalendarAppointment> calendarAppointments;
    private List<StudentClasses> classes;
    private List<StudentDocs> docs;*/

    public Student(){
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String student_number_id) {
        this.numberID = student_number_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String student_number) {
        this.number = student_number;
    }

    /*public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

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
