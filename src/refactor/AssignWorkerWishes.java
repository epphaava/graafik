package refactor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import objects.Shift;
import objects.Worker;

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
        assignSooviTööpäevad(töötaja.getSooviTööPäevad(), scheduleMatrix, töötaja.getEmployeeId());
        assignSpecificShifts(töötaja.getPuhkusePäevad(), scheduleMatrix, töötaja.getEmployeeId(), new Shift(0, "P"));
        assignSpecificShifts(töötaja.getSooviPuhkePäevad(), scheduleMatrix, töötaja.getEmployeeId(), new Shift(0, "D"));
    }

    // Assign Vacation and desired vacation days
    public static void assignSpecificShifts(List<Integer> days, Shift[][] scheduleMatrix, int workerId, Shift shift) {
        for (Integer day : days) {
            try {
                scheduleMatrix[day][workerId] = shift;
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
    }

    // Assign Work shifts
    private static void assignSooviTööpäevad(HashMap<Integer, Shift> workDays, Shift[][] scheduleMatrix, int workerId) {
        for (Map.Entry<Integer, Shift> entry : workDays.entrySet()) {
            int day = entry.getKey() - 1; // Adjust for 0-based index
            Shift shift = entry.getValue();
            scheduleMatrix[day][workerId] = shift;
        }

    }
  
}