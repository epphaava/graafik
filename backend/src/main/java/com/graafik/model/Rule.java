package com.graafik.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Rule {

    @JsonProperty("shift")
    private Shift shift;

    @JsonProperty("daysApplied")
    private List<Integer> daysApplied;

    @JsonProperty("perDay")
    private int perDay;

    @JsonProperty("restDays")
    private int restDays;

    @JsonProperty("priority")
    private PriorityType priority;

    // No-args constructor (Required for Jackson)
    public Rule() {}

    // Getters
    public Shift getShift() {
        return shift;
    }

    public List<Integer> getDaysApplied() {
        return daysApplied;
    }

    public int getPerDay() {
        return perDay;
    }

    public int getRestDays() {
        return restDays;
    }

    public PriorityType getPriority() {
        return priority;
    }

    // Setters
    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void setDaysApplied(List<Integer> daysApplied) {
        this.daysApplied = daysApplied;
    }

    public void setPerDay(int perDay) {
        this.perDay = perDay;
    }

    public void setRestDays(int restDays) {
        this.restDays = restDays;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    // Enum for priority
    public enum PriorityType {
        /// Unbreakable rule
        Critical,
        /// Can break if very needed
        High,
        /// Can break
        Medium,
        /// Nice to have
        Low
    }
}
