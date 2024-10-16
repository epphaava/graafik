package com.backend.graafik.schedule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

public class AssignWorkerWishes {

  // Initialize empty matrix
  public static Shift[][] initializeScheduleMatrix(int daysInMonth, int numberOfWorkers) {
    Shift[][] scheduleMatrix = new Shift[daysInMonth][numberOfWorkers];
    for (int i = 0; i < daysInMonth; i++) {
      Arrays.fill(scheduleMatrix[i], new Shift(0, ""));
    }
    return scheduleMatrix;
  }

  // Fill in Workers requested days
  public static void assignWorkerWishes(List<Worker> töötajateNimekiri, Shift[][] scheduleMatrix) {
    for (Worker töötaja : töötajateNimekiri) {
      assignShifts(töötaja, scheduleMatrix);
    }
  }

  // Get day types
  public static void assignShifts(Worker töötaja, Shift[][] scheduleMatrix) {
    assignSooviTööpäevad(töötaja, scheduleMatrix);
    assignSpecificShifts(töötaja.getVacationDays(), scheduleMatrix, töötaja.getEmployeeId(), new Shift(0, Shift.PUHKUS));
    assignSpecificShifts(töötaja.getDesiredVacationDays(), scheduleMatrix, töötaja.getEmployeeId(), new Shift(0, Shift.SOOVI_PUHKUS));
    assignSpecificShifts(töötaja.getTrainingDays(), scheduleMatrix, töötaja.getEmployeeId(), new Shift(8, Shift.KOOLITUS));
  }

  // Assign Vacation and desired vacation days
  public static void assignSpecificShifts(List<Integer> days, Shift[][] scheduleMatrix, int workerId, Shift shift) {
    for (int day : days) {
      if (scheduleMatrix.length > day - 1) {
        scheduleMatrix[day - 1][workerId] = shift;
      }
    }
  }

  // Assign Work shifts
  private static void assignSooviTööpäevad(Worker worker, Shift[][] scheduleMatrix) {
    HashMap<Integer, Shift> workDays = worker.getDesiredWorkDays();
    int workerId = worker.getEmployeeId();

    for (Map.Entry<Integer, Shift> entry : workDays.entrySet()) {
      int day = entry.getKey() - 1; // Adjust for 0-based index
      Shift shift = entry.getValue();
      if (day == scheduleMatrix.length - 1 && shift.getDuration() == 24)
        shift = new Shift(16, shift.getCategory());
      scheduleMatrix[day][workerId] = shift;
    }

  }
}