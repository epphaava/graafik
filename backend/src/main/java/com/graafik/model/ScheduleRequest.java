package com.graafik.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleRequest {

    @JsonProperty("workers")
    private List<WorkerDto> workers;

    @JsonProperty("shifts")
    private List<Shift> shifts;

    @JsonProperty("month")
    private int month;

    @JsonProperty("fullTimeHours")
    private int fullTimeHours;

    // Constructor with all fields
    public ScheduleRequest() {}

    // Getters
    public List<WorkerDto> getWorkers() {
        return workers;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public int getMonth() {
        return month;
    }

    public int getFullTimeHours() {
        return fullTimeHours;
    }

    // Setters
    public void setWorkers(List<WorkerDto> workers) {
        this.workers = workers;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setFullTimeHours(int fullTimeHours) {
        this.fullTimeHours = fullTimeHours;
    }
}
