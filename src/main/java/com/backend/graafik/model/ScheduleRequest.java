package com.backend.graafik.model;

import java.util.List;

import java.time.*;
import java.time.Month;
import java.time.temporal.ChronoField;

public class ScheduleRequest {
    public List<Worker> Workers;
    public List<Shift> Shifts;
    public int Month;
    public int Year;
    public int FullTimeHours;
    public List<Rule> Rules;

    public List<Worker> getWorkers() {
        return Workers;
    }

    public void setWorkers(List<Worker> workers) {
        Workers = workers;
    }

    public List<Shift> getShifts() {
        return Shifts;
    }

    public void setShifts(List<Shift> shifts) {
        Shifts = shifts;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getFullTimeHours() {
        return FullTimeHours;
    }

    public void setFullTimeHours(int fullTimeHours) {
        FullTimeHours = fullTimeHours;
    }

    public List<Rule> getRules() {
        return Rules;
    }

    public void setRules(List<Rule> rules) {
        Rules = rules;
    }

    public ScheduleRequest(List<Worker> workers, List<Shift> shifts, int month, int year, int fullTimeHours, List<Rule> rules) {
        Workers = workers;
        Shifts = shifts;
        Month = month;
        Year = year;
        FullTimeHours = fullTimeHours;
        Rules = rules;
    }
}
