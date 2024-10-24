package com.backend.graafik.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

public class EnforceShifts {
  // Assign a shift to a worker who has a desired vacation day ("D")
  public static void assignShiftToWorkerWithD(Shift[][] scheduleMatrix, int dayIndex, List<Shift> todayShifts,
      List<Shift> tomorrowShifts, List<Shift> dayAfterTomorrowShifts, Shift shift, List<Worker> workers) {
    List<Worker> sortedWorkers = new ArrayList<>(workers);
    sortedWorkers.sort(Comparator.comparingDouble(Worker::getPercentageWorked));

    for (Worker worker : sortedWorkers) {
      if (!scheduleMatrix[dayIndex][worker.getEmployeeId()].getCategory().equals(Shift.SOOVI_PUHKUS) || !scheduleMatrix[dayIndex][worker.getEmployeeId()].getCategory().equals(Shift.TÜHI))
        continue;

      Shift tomorrowShift = tomorrowShifts.isEmpty() ? new Shift(0, "") : tomorrowShifts.get(worker.getEmployeeId());
      Shift dayAfterTomorrowShift = dayAfterTomorrowShifts.isEmpty() ? new Shift(0, "")
          : dayAfterTomorrowShifts.get(worker.getEmployeeId());

      if (isValidShiftForD(scheduleMatrix, dayIndex, tomorrowShift, dayAfterTomorrowShift, shift, worker)) {
        if (shift.getDuration() == 24) {
          AssignWorkerWishes.assignSpecificShifts(Arrays.asList(dayIndex + 2, dayIndex + 3), scheduleMatrix,
              worker.getEmployeeId(),
              new Shift(0, Shift.KEELATUD));
          if (dayIndex == scheduleMatrix.length - 1) shift = new Shift(16, shift.getCategory());
          worker.setNumOf24hShifts(worker.getNumOf24hShifts() - 1);
        }
        scheduleMatrix[dayIndex][worker.getEmployeeId()] = shift;
        worker.setHoursBalance(worker.getHoursBalance() + shift.getDuration());
        break;
      }
    }
  }

  // Check if the shift can be assigned to a worker with "D"
  public static boolean isValidShiftForD(Shift[][] scheduleMatrix, int dayIndex, Shift tomorrowShift, Shift dayAfterTomorrowShift, Shift shift, Worker worker) {
    if (!HelperMethods.atLeastTwoRestdays(scheduleMatrix, dayIndex, worker.getEmployeeId()) || !HelperMethods.atMostXDaysInARow(scheduleMatrix, dayIndex, worker.getEmployeeId(), 2)) return false;

    if (shift.getDuration() == 24) {
      return (tomorrowShift.getCategory().equals(Shift.TÜHI) || tomorrowShift.getCategory().equals(Shift.SOOVI_PUHKUS)) && dayAfterTomorrowShift.getDuration() == 0 && worker.getHoursBalance() + worker.getLastMonthBalance() <= -20 && worker.getNumOf24hShifts() != 0 && (dayIndex == 0 || scheduleMatrix[dayIndex - 1][worker.getEmployeeId()].getDuration() == 0);
    }
    if (shift.getDuration() == 10) {
      return tomorrowShift.getDuration() != 24 && tomorrowShift.getDuration() != 16  && worker.getHoursBalance() + worker.getLastMonthBalance() <= -4;
    }
    return false;
  }
}