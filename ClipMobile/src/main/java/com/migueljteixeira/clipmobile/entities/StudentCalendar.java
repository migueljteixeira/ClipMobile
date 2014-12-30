package com.migueljteixeira.clipmobile.entities;

public class StudentCalendar extends Entity {

    private String name, date, hour, rooms, number;

    public StudentCalendar() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public String getRooms() { return rooms; }
    public void setRooms(String rooms) { this.rooms = rooms; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
}
