package com.graafik.schedule;

import com.graafik.data.WorkerConverter;
import com.graafik.model.AssignedShift;
import com.graafik.model.ScheduleRequest;
import com.graafik.model.Shift;
import com.graafik.model.Worker;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CreateSchedule {
    public static void main(String[] args) {

        ScheduleRequest request = new ScheduleRequest(WorkerConverter.createWorkersList(152), new ArrayList<>(), 2, 2025, 152, new ArrayList<>());
        createSchedule(request);

    }

    public static void createSchedule(ScheduleRequest scheduleRequest) {

        List<List<List<AssignedShift>>> allPossibleSchedules = generateAllPossibleSchedules(scheduleRequest);

        // Print the results
        for (List<List<AssignedShift>> combination : allPossibleSchedules) {
            for (List<AssignedShift> day : combination) {
                for (AssignedShift assignedShift : day) {
                    System.out.print(assignedShift.getWorker() +  " " + assignedShift.getShift() +", ");
                }
                System.out.println();
            }
            System.out.println("---");
        }

    }

    public static List<List<List<AssignedShift>>> generateAllPossibleSchedules(ScheduleRequest scheduleRequest) {

        // get a list of shifts for every day of the month

        List<List<List<AssignedShift>>> allCombinations = new ArrayList<>();

        // Recursively generate all combinations
        generateCombinationsRecursive(scheduleRequest, 0, new ArrayList<>(), allCombinations);

        return allCombinations;

    }

    private static void generateCombinationsRecursive(ScheduleRequest scheduleRequest, int date,
                                                      List<List<AssignedShift>> currentSchedule, List<List<List<AssignedShift>>> allCombinations) {

        int daysInMonth = YearMonth.of(scheduleRequest.Year, scheduleRequest.Month).lengthOfMonth();

        if (date == daysInMonth) {
            // All days processed, add the combination
            allCombinations.add(new ArrayList<>(currentSchedule));
            return;
        }

        // Generate currentDayAllPossibleShiftAssignments of workers for the current shift count
        List<List<AssignedShift>> currentDayAllPossibleShiftAssignments = getPermutations(scheduleRequest, date);

        // go through all the generated possible assignments for the current date
        for (List<AssignedShift> currentDayShiftAssignments : currentDayAllPossibleShiftAssignments) {

            if (validator(currentSchedule, currentDayShiftAssignments) < -50) continue;

            currentSchedule.add(currentDayShiftAssignments);

            // rn to not wait for all possibilities
            if (allCombinations.size() == 3) break;

            // if rating is fine go to next date do the whole thing again
            generateCombinationsRecursive(scheduleRequest, date + 1, currentSchedule, allCombinations);

            // recursion done, remove last assignments list that was added and try with the next one
            currentSchedule.removeLast();
        }
    }

    public static List<List<AssignedShift>> getPermutations(ScheduleRequest scheduleRequest, int date) {
        List<List<AssignedShift>> result = new ArrayList<>();

        List<Shift> currentDayShifts = getCurrentDayShifts(scheduleRequest, date);

        permuteHelper(scheduleRequest, currentDayShifts, new ArrayList<>(), result);
        return result;
    }

    private static void permuteHelper(ScheduleRequest scheduleRequest, List<Shift> currentDayShifts, List<AssignedShift> currentCombination, List<List<AssignedShift>> result) {

        // if all shifts have a worker assigned for them, return
        if (currentCombination.size() == currentDayShifts.size()) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (Worker worker : scheduleRequest.Workers) {
            AssignedShift assignedShift = new AssignedShift(worker, currentDayShifts.get(currentCombination.size()));
            if (!containsWorker(currentCombination, worker)) {
                currentCombination.add(assignedShift);
                permuteHelper(scheduleRequest, currentDayShifts, currentCombination, result);
                currentCombination.removeLast();
            }

        }
    }

    // TODO: korda see genemine, või mõelda kuidas struktuur olla võiks
    public static List<Shift> getCurrentDayShifts(ScheduleRequest scheduleRequest, int date) {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift(24, Shift.OSAKOND));
        shifts.add(new Shift(24, Shift.INTENSIIV));
        shifts.add(new Shift(8, Shift.OSAKOND));

        return shifts;
    }

    // TODO
    public static int validator(List<List<AssignedShift>> currentSchedule, List<AssignedShift> currentDayShiftAssignments) {
        for (AssignedShift assignedShift : currentDayShiftAssignments) {
            assignedShift.getShift
        }
        return 0;
    }

    public static boolean containsWorker(List<AssignedShift> assignedShifts, Worker worker) {
        for (AssignedShift assignedShift : assignedShifts) {
            if (assignedShift.worker.equals(worker)) {
                return true;
            }
        }
        return false;
    }
}