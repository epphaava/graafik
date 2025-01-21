package com.backend.graafik.schedule;

import com.backend.graafik.data.WorkerConverter;
import com.backend.graafik.model.ScheduleRequest;
import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

import java.time.YearMonth;
import java.util.*;

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
        List<Worker> workers = scheduleRequest.Workers;

        List<List<List<Integer>>> allCombinations = new ArrayList<>();

        // Recursively generate all combinations
        generateCombinationsRecursive(workers.size(), shiftsForWeekDays, 0, new ArrayList<>(), allCombinations);

        return allCombinations;

    }

    private static void generateCombinationsRecursive(int workers, List<List<Shift>> shiftsForWeekDays, int dayIndex,
                                                      List<List<Integer>> currentCombination, List<List<List<Integer>>> allCombinations) {
        if (dayIndex == shiftsForWeekDays.size()) {
            // All days processed, add the combination
            allCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        // Get shift count for the current day
        int shiftCount = shiftsForWeekDays.get(dayIndex).size();

        // Generate permutations of workers for the current shift count
        List<List<Integer>> permutations = getPermutations(workers, shiftCount);

        for (List<Integer> perm : permutations) {
            currentCombination.add(perm);
            System.out.println(perm);
            if (allCombinations.size() == 3) break;
            generateCombinationsRecursive(workers, shiftsForWeekDays, dayIndex + 1, currentCombination, allCombinations);
            currentCombination.removeLast();
        }
    }

    public static List<List<Integer>> getPermutations(int workers, int shiftCount) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> workerIndexes = new ArrayList<>();
        for (int i = 0; i < workers; i++) {
            workerIndexes.add(i);
        }
        permuteHelper(workerIndexes, shiftCount, new ArrayList<>(), result);
        return result;
    }

    private static void permuteHelper(List<Integer> workers, int size, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == size) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (Integer worker : workers) {
            if (!current.contains(worker)) {
                current.add(worker);
                permuteHelper(workers, size, current, result);
                current.removeLast();
            }
        }
    }
}