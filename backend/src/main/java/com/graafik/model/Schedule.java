package com.graafik.model;

import java.util.List;

public class Schedule {
    private int month;
    private int year;
    private List<DaySchedule> daySchedules;

    // Constructor
    public Schedule(int month, int year, List<DaySchedule> daySchedules) {
        this.month = month;
        this.year = year;
        this.daySchedules = daySchedules;
    }

    // Getter for month
    public int getMonth() {
        return month;
    }

    // Setter for month
    public void setMonth(int month) {
        this.month = month;
    }

    // Getter for year
    public int getYear() {
        return year;
    }

    // Setter for year
    public void setYear(int year) {
        this.year = year;
    }

    // Getter for daySchedules
    public List<DaySchedule> getDaySchedules() {
        return daySchedules;
    }

    // Setter for daySchedules
    public void setDaySchedules(List<DaySchedule> daySchedules) {
        this.daySchedules = daySchedules;
    }

}