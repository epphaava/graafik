package com.backend.graafik.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.backend.graafik.model.RecordedShift;

import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

public class AssignShifts {

    // Fill in rest of the shifts
    public static void fillShifts(Shift[][] scheduleMatrix, Shift[][] scheduleMatrixOriginal, List<Worker> workers, List<RecordedShift> recordedShifts, RecordedShift lastRecordedShift, Map<Integer, List<Worker>> unusedWorkers) {
        int daysInMonth = scheduleMatrix.length;
        for (int dayIndex = lastRecordedShift.getShiftDate(); dayIndex < daysInMonth; dayIndex++) {

            List<Shift> todayShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex);
            List<Shift> tomorrowShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex + 1);
            List<Shift> dayAfterTomorrowShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex + 2);
            List<Shift> dayAfterAfterTomorrowShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex + 3);

            // Assign PikkPäev shift
            Shift longShift = new Shift(24, Shift.PIKK_PÄEV);

            if (!todayShifts.contains(longShift)) {
                assignShiftForDay(scheduleMatrix, scheduleMatrixOriginal, dayIndex, todayShifts, tomorrowShifts, dayAfterTomorrowShifts, dayAfterAfterTomorrowShifts, longShift,
                        workers, recordedShifts, unusedWorkers);
            }
            /*if (!todayShifts.contains(longShift)) {
                EnforceShifts.assignShiftToWorkerWithD(scheduleMatrix, scheduleMatrixOriginal, dayIndex, todayShifts, tomorrowShifts,
                        dayAfterTomorrowShifts, longShift, workers, recordedShifts, unusedWorkers);
            }*/

            // if shift still not there, backtrack
            if (!todayShifts.contains(longShift)) {

                dayIndex = HelperMethods.backtrack(recordedShifts, scheduleMatrix, scheduleMatrixOriginal, workers, unusedWorkers) - 1;
                // If there is nothing to backtrack
                if (dayIndex == -1) break;
                continue;
            }

            // Assign Short shift
            Shift shortShift = new Shift(8, Shift.LÜHIKE_PÄEV);
            if (!todayShifts.contains(shortShift)) {
                assignShiftForDay(scheduleMatrix, scheduleMatrixOriginal, dayIndex, todayShifts, tomorrowShifts, dayAfterTomorrowShifts, dayAfterAfterTomorrowShifts, shortShift,
                        workers, recordedShifts, unusedWorkers);
            }
            /*if (!todayShifts.contains(shortShift)) {
                EnforceShifts.assignShiftToWorkerWithD(scheduleMatrix, scheduleMatrixOriginal, dayIndex, todayShifts, tomorrowShifts,
                        dayAfterTomorrowShifts, shortShift, workers, recordedShifts, unusedWorkers);
            }*/

            // if shift still not there, backtrack
            if (!todayShifts.contains(shortShift)) {

                dayIndex = HelperMethods.backtrack(recordedShifts, scheduleMatrix, scheduleMatrixOriginal, workers, unusedWorkers) - 1;
                // If there is nothing to backtrack
                if (dayIndex == -1) break;
                continue;
            }

            //CheckMissingShifts(todayShifts, dayIndex, recordedShifts);
            PrintMissingShifts(todayShifts, dayIndex);
        }
    }

    // Assign needed shifts for the day
    public static void assignShiftForDay(Shift[][] scheduleMatrix, Shift[][] scheduleMatrixOriginal, int dayIndex, List<Shift> todayShifts,
                                         List<Shift> tomorrowShifts, List<Shift> dayAfterTomorrowShifts, List<Shift> dayAfterAfterTomorrowShifts, Shift shift, List<Worker> workers, List<RecordedShift> recordedShifts, Map<Integer, List<Worker>> unusedWorkers) {



        List<Worker> sortedWorkers = new ArrayList<>(unusedWorkers.get(dayIndex));
        sortedWorkers.sort(Comparator.comparingDouble(Worker::getPercentageWorked));

        for (Worker worker : sortedWorkers) {
            Shift todayShift = todayShifts.get(worker.getEmployeeId()); // that worker's shift today
            Shift tomorrowShift = tomorrowShifts.isEmpty() ? new Shift(0, "") : tomorrowShifts.get(worker.getEmployeeId());
            Shift dayAfterTomorrowShift = dayAfterTomorrowShifts.isEmpty() ? new Shift(0, "") : dayAfterTomorrowShifts.get(worker.getEmployeeId());
            Shift dayAfterAfterTomorrowShift = dayAfterAfterTomorrowShifts.isEmpty() ? new Shift(0, "") : dayAfterAfterTomorrowShifts.get(worker.getEmployeeId());

            if (isValidShift(scheduleMatrix, dayIndex, todayShift, tomorrowShift, dayAfterTomorrowShift, dayAfterAfterTomorrowShift, shift, worker)) {
                if (shift.getDuration() == 24) {
                    AssignWorkerWishes.assignSpecificShifts(Arrays.asList(dayIndex + 2, dayIndex + 3), scheduleMatrix,
                            worker.getEmployeeId(),
                            new Shift(0, Shift.KEELATUD));
                    if (dayIndex == scheduleMatrix.length - 1) {
                        shift = new Shift(16, shift.getCategory());
                    }
                    worker.setNumOf24hShifts(worker.getNumOf24hShifts() - 1);

                }
                unusedWorkers.get(dayIndex).remove(worker);

                recordedShifts.add(new RecordedShift(dayIndex, worker, recordedShifts.isEmpty() ? 0 : recordedShifts.getLast().getScheduleScore()));

                scheduleMatrix[dayIndex][worker.getEmployeeId()] = shift;
                worker.setQuarterBalance(worker.getQuarterBalance() + shift.getDuration());

                break;
            }

        }
    }

    // Check if assigning a Shift is possible
    public static boolean isValidShift(Shift[][] scheduleMatrix, int dayIndex, Shift todayShift, Shift tomorrowShift, Shift dayAfterTomorrowShift, Shift dayAfterAfterTomorrowShift, Shift shift, Worker worker) {
        if (!HelperMethods.atLeastTwoRestdays(scheduleMatrix, dayIndex, worker.getEmployeeId()) || !HelperMethods.atMostXDaysInARow(scheduleMatrix, dayIndex, worker.getEmployeeId(), 2)) {
            return false;
        }
        if (shift.getDuration() == 24) {
            return todayShift.getCategory().equals(Shift.TÜHI)
                    && tomorrowShift.getCategory().equals(Shift.TÜHI)
                    && dayAfterTomorrowShift.getDuration() == 0
                    && dayAfterAfterTomorrowShift.getDuration() == 0
                    && worker.getQuarterBalance() + worker.getLastMonthBalance() <= -24
                    && worker.getNumOf24hShifts() != 0
                    && (dayIndex == 0 || scheduleMatrix[dayIndex - 1][worker.getEmployeeId()].getDuration() == 0);
        }
        if (shift.getDuration() == 8) {
            return todayShift.getCategory().equals(Shift.TÜHI)
                    && tomorrowShift.getDuration() != 24
                    && worker.getQuarterBalance() + worker.getLastMonthBalance() <= -4;
        }
        return false;
    }

    public static void CheckMissingShifts(List<Shift> todayShifts, int dayIndex, List<RecordedShift> recordedShifts) {
        Shift longShift = new Shift(24, Shift.PIKK_PÄEV);
        Shift shortShift = new Shift(8, Shift.LÜHIKE_PÄEV);
        if (!todayShifts.contains(shortShift) || !todayShifts.contains(longShift)) {
            recordedShifts.get(recordedShifts.size() - 1).setScheduleScore(recordedShifts.get(recordedShifts.size() - 1).getScheduleScore() - 50);
        }
    }

    public static void PrintMissingShifts(List<Shift> todayShifts, int dayIndex) {
        Shift longShift = new Shift(24, Shift.PIKK_PÄEV);
        Shift shortShift = new Shift(8, Shift.LÜHIKE_PÄEV);

        if (!todayShifts.contains(shortShift)) System.out.println("Kuupäeval " + dayIndex + " puudu lühike vahetus");
        if (!todayShifts.contains(longShift)) System.out.println("Kuupäeval " + dayIndex + " puudu pikk vahetus");
    }

}
