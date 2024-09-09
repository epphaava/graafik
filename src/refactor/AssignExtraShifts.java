package refactor;


import objects.Shift;
import objects.Worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AssignExtraShifts {
    public static void AddExtraShifts(Shift[][] scheduleMatrix, int daysInMonth, List<Worker> workers, int firstDayOfMonth) {
        var filteredWorkers = FilterWorkers(workers);

        for (int dayIndex = 0; dayIndex < daysInMonth; dayIndex++) {
            
            int weekday = HelperMethods.getDay(dayIndex - 1, firstDayOfMonth);
            if (weekday == 0 && weekday == 6) continue;

            List<Shift> todayShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex);
            List<Shift> tomorrowShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex + 1);
            List<Shift> dayAfterTomorrowShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex + 2);
            Shift lühikeShift = new Shift(8, Shift.LÜHIKE_PÄEV);


            assignShiftForDay(scheduleMatrix, dayIndex, todayShifts, tomorrowShifts, dayAfterTomorrowShifts, lühikeShift, filteredWorkers);
            todayShifts = HelperMethods.getShiftsForDay(scheduleMatrix, dayIndex);
            assignShiftForDay(scheduleMatrix, dayIndex, todayShifts, tomorrowShifts, dayAfterTomorrowShifts, lühikeShift, filteredWorkers);
            filteredWorkers = FilterWorkers(filteredWorkers);
        }
    }

    private static List<Worker> FilterWorkers(List<Worker> workers) {
        var negativeWorkers = workers.stream().filter(w -> w.getHoursBalance() <= -8).collect(Collectors.toList());
        negativeWorkers.sort(Comparator.comparingDouble(Worker::getHoursBalance));
        return  negativeWorkers;
    }

    // Assign needed shifts for the day
    public static void assignShiftForDay(Shift[][] scheduleMatrix, int dayIndex, List<Shift> todayShifts, List<Shift> tomorrowShifts, List<Shift> dayAfterTomorrowShifts, Shift shift, List<Worker> workers) {
        List<Worker> sortedWorkers = new ArrayList<>(workers);
        sortedWorkers.sort(Comparator.comparingDouble(Worker::getPercentageWorked));

        for (Worker worker : sortedWorkers) {

        Shift todayShift = todayShifts.get(worker.getEmployeeId()); // that worker's shift today
        Shift tomorrowShift = tomorrowShifts.isEmpty() ? new Shift(0, "") : tomorrowShifts.get(worker.getEmployeeId());
        Shift dayAfterTomorrowShift = dayAfterTomorrowShifts.isEmpty() ? new Shift(0, "")
            : dayAfterTomorrowShifts.get(worker.getEmployeeId());

        if (isValidShift(todayShift, tomorrowShift, dayAfterTomorrowShift, shift)) {
            if (dayIndex < 6 || HelperMethods.atLeastTwoRestdays(scheduleMatrix, dayIndex, worker.getEmployeeId())) {
            if (shift.getDuration() == 24) {
                AssignWorkerWishes.assignSpecificShifts(Arrays.asList(dayIndex + 2, dayIndex + 3), scheduleMatrix,
                    worker.getEmployeeId(),
                    new Shift(0, Shift.KEELATUD));

                if (dayIndex == scheduleMatrix.length - 1) shift = new Shift(16, shift.getCategory());

            }

            scheduleMatrix[dayIndex][worker.getEmployeeId()] = shift;

            worker.setHoursWorked(shift.getDuration());
            worker.setHoursBalance(worker.getHoursBalance() + shift.getDuration());
            worker.setPercentageWorked((shift.getDuration() * 100) / worker.getWorkLoadHours());

            break;
            }
        }
    }
  }

  public static boolean isValidShift(Shift todayShift, Shift tomorrowShift, Shift dayAfterTomorrowShift, Shift shift) {
    if (shift.getDuration() == 24) {
      return todayShift.getCategory().equals(Shift.TÜHI) && tomorrowShift.getCategory().equals(Shift.TÜHI)
          && dayAfterTomorrowShift.getDuration() == 0;
    }
    if (shift.getDuration() == 8) {
      return todayShift.getCategory().equals(Shift.TÜHI) && tomorrowShift.getDuration() != 24;
    }
    return false;
  }
}