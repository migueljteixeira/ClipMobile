package com.migueljteixeira.clipmobile.entities;

public class StudentScheduleClass extends Entity {

    private String name;
    private String name_min;
    private String type;
    private String hour_start;
    private String hour_end;
    private String room;

    public StudentScheduleClass() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name;}

    public String getNameMin() { return name_min; }
    public void setNameMin(String name_min) { this.name_min = name_min; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getHourStart() { return hour_start; }
    public void setHourStart(String hour_start) { this.hour_start = hour_start; }

    public String getHourEnd() { return hour_end; }
    public void setHourEnd(String hour_end) { this.hour_end = hour_end; }

    public String getRoom() { return room; }
    public void setRoom(String room) {this.room = room; }

}
