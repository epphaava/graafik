package com.backend.graafik.schedule;

import com.backend.graafik.data.WorkerConverter;
import com.backend.graafik.model.ScheduleRequest;
import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CreateSchedule {
    public static void main(String[] args) {

        ScheduleRequest request = new ScheduleRequest(WorkerConverter.createWorkersList(152), new ArrayList<>(), 2, 2025, 152, new ArrayList<>());
        createSchedule(request);

    }

    public static void createSchedule(ScheduleRequest scheduleRequest) {

        List<List<List<Integer>>> allPossibleSchedules = generateAllPossibleSchedules(scheduleRequest);


        for (List<List<Integer>> combination : allPossibleSchedules) {
            for (List<Integer> day : combination) {
                System.out.println(day);
            }
            System.out.println("---");
        }

    }

    public static List<List<List<Integer>>> generateAllPossibleSchedules(ScheduleRequest scheduleRequest) {


        List<Shift> shiftsForDay = new ArrayList<>();
        shiftsForDay.add(new Shift(24, Shift.OSAKOND));
        shiftsForDay.add(new Shift(24, Shift.INTENSIIV));
        shiftsForDay.add(new Shift(8, Shift.OSAKOND));

        int daysInMonth = YearMonth.of(scheduleRequest.Year, scheduleRequest.Month).lengthOfMonth();

        List<List<Shift>> shiftsForWeekDays = new ArrayList<>();

        for (int i = 0; i < daysInMonth; i++) {
            shiftsForWeekDays.add(shiftsForDay);
        }

        List<List<List<Integer>>> allCombinations = new ArrayList<>();

        // Recursively generate all combinations
        generateCombinationsRecursive(scheduleRequest, shiftsForWeekDays, 0, new ArrayList<>(), allCombinations);

        return allCombinations;

    }

    private static void generateCombinationsRecursive(ScheduleRequest scheduleRequest, List<List<Shift>> shiftsForWeekDays, int date,
                                                      List<List<Integer>> currentSchedule, List<List<List<Integer>>> allCombinations) {
        if (date == shiftsForWeekDays.size()) {
            // All days processed, add the combination
            allCombinations.add(new ArrayList<>(currentSchedule));
            return;
        }

        // Get shift count for the current day
        int shiftCount = shiftsForWeekDays.get(date).size();

        // Generate currentDayAllPossibleShiftAssignments of workers for the current shift count
        List<List<Integer>> currentDayAllPossibleShiftAssignments = getPermutations(scheduleRequest, shiftCount);

        for (List<Integer> currentDayShiftAssignments : currentDayAllPossibleShiftAssignments) {


            int assignmentRating = assignmentRating(scheduleRequest, currentSchedule, currentDayShiftAssignments, shiftsForWeekDays.getFirst(), date);

            if (assignmentRating <= -100) continue;

            currentSchedule.add(currentDayShiftAssignments);

            if (allCombinations.size() == 3) break;
            generateCombinationsRecursive(scheduleRequest, shiftsForWeekDays, date + 1, currentSchedule, allCombinations);
            currentSchedule.removeLast();
        }
    }

    public static List<List<Integer>> getPermutations(ScheduleRequest scheduleRequest, int shiftCount) {
        List<List<Integer>> result = new ArrayList<>();

        permuteHelper(scheduleRequest, shiftCount, new ArrayList<>(), result);
        return result;
    }

    private static void permuteHelper(ScheduleRequest scheduleRequest, int shiftCount, List<Integer> currentCombination, List<List<Integer>> result) {
        if (currentCombination.size() == shiftCount) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (Worker worker : scheduleRequest.Workers) {
            if (!currentCombination.contains(worker.getEmployeeId())) {
                currentCombination.add(worker.getEmployeeId());
                permuteHelper(scheduleRequest, shiftCount, currentCombination, result);
                currentCombination.removeLast();
            }

        }
    }

    public static int assignmentRating(ScheduleRequest scheduleRequest, List<List<Integer>> currentSchedule, List<Integer> newAssignments, List<Shift> shifts, int date) {
        // TODO
        // get rating

        // 2. worker requests
        // 3. rules

        return 0;
    }
}