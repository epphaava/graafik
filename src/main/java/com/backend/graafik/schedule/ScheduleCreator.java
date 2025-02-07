package com.backend.graafik.schedule;

import com.backend.graafik.data.WorkerConverter;
import com.backend.graafik.model.RecordedShift;
import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

import java.util.*;

public class ScheduleCreator {
    //TODO: Edgecases
    // Case 1 --> End of quarter worker has +6 or +7. if worker has 2 8hr shifts, remove 1 and increase the other to 10, 
    // removed shift check if someone is missing 8-10 hrs and try to assign first on same day if cant then on other day for the same worker

    // Case 2 --> End of quarter is -7, check if has 10hr or 9hr shift, take -1hr and add somewhere +8hr shift
    // Case 3 --> End of quarter is -4, check if has 2x10hr shifts, change them to 8hr and add 1 more 8hr shift
    // Case 4 --> End of quarter is +4 ?
    // Case 5 --> End of quarter is +2 ?
    public static void main(String[] args) {

        int daysInMonth = 31;
        int firstDayOfMonth = 3;
        int fullTimeHours = 176;
        List<Integer> holidays = new ArrayList<>(Arrays.asList(1,2,3,6));

        List<Worker> workersList = WorkerConverter.createWorkersList(fullTimeHours);

        List<RecordedShift> recordedShifts = new ArrayList<>();
        RecordedShift lastRecordedShift = new RecordedShift(0, workersList.getFirst(), 0);

        boolean lastMonthOfQuarter = true;

        Shift[][] scheduleMatrixOriginal = AssignWorkerWishes.initializeScheduleMatrix(daysInMonth, workersList.size());


        Shift[][] scheduleMatrix = AssignWorkerWishes.initializeScheduleMatrix(daysInMonth, workersList.size());

        Map<Integer, List<Worker>> unusedWorkers = new HashMap<>();
        for (int i = 0; i < scheduleMatrix.length; i++) {
            List<Worker> workersCopy = new ArrayList<>();
            for (int j = 0; j < scheduleMatrix[i].length; j++) {
                if (!scheduleMatrix[i][j].getCategory().equals(Shift.KEELATUD) && !scheduleMatrix[i][j].getCategory().equals(Shift.PUHKUS) && !scheduleMatrix[i][j].getCategory().equals(Shift.KOOLITUS)) workersCopy.add(workersList.get(j));

            }
            unusedWorkers.put(i, workersCopy);
        }

        // Step 1
        AssignWorkerWishes.assignWorkerWishes(workersList, scheduleMatrix);
        AssignWorkerWishes.assignWorkerWishes(workersList, scheduleMatrixOriginal);

        // Step 2 KEELATUD päevad
        AddForbiddenDays.addForbiddenDays(workersList, scheduleMatrix);
        AddForbiddenDays.addForbiddenDays(workersList, scheduleMatrixOriginal);

        // Step 3 Muuda koormuse põhjal
        ChangeWorkLoads.changeWorkLoads(workersList, firstDayOfMonth);

        // Step 4 fill shifts
        AssignShifts.fillShifts(scheduleMatrix, scheduleMatrixOriginal, workersList, recordedShifts, lastRecordedShift, unusedWorkers);


        // Step 5 kui rahval < -8h jääk siis vaatame kuhu saab neid assginida --> ja assgnima ainult tööpäevadle sest nv olemas juba
        AssignExtraShifts.addExtraShifts(scheduleMatrix, daysInMonth, workersList, firstDayOfMonth);

        // Step 6 if kvartaliviimane kuu ss lisa meetod et teha vajadusel 8h vahetus --> 10h vahetuseks
        if (lastMonthOfQuarter) Quarter.QuarterBalance(scheduleMatrix, workersList);
        else Month.MonthlyBalance(scheduleMatrix, workersList);


        // Deal with Edgecases

        // Export matrix
        VisualizeResults.MatrixToCSV(scheduleMatrix, "./tulemus.csv", workersList);
        VisualizeResults.printSchedule(scheduleMatrix, workersList);

    }
}